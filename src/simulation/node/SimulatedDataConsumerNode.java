package simulation.node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import node.BankNode;
import node.BrokerNode;
import node.CurrencyTransferAuthorization;
import node.DataConsumerNode;
import node.DataProviderNode;
import node.DataRequest;
import node.NodeManager;
import node.NodeManager.NodeGroup;
import node.Transaction;
import simulation.Simulation;
import simulation.util.SimulationSigned;
import data.Data;

public class SimulatedDataConsumerNode extends SimulatedNode implements DataConsumerNode {

	private final List<BankNode> bankNodes = new ArrayList<BankNode>();
	
	private final Set<UUID> currencyUnitIds = new HashSet<UUID>();
	
	
	public SimulatedDataConsumerNode(UUID nodeId, NodeManager nodeManager, Map<UUID, Data> dataStore)
	{
		super(nodeId, nodeManager, dataStore);
		
		NodeGroup<BankNode> bankNodeGroup = nodeManager.getBankNodesForNode(nodeId); 
		while(bankNodeGroup.hasMoreNodes())
		{
			bankNodes.addAll(bankNodeGroup.getNodes());
		}
		
		for(BankNode bankNode : bankNodes)
		{
			currencyUnitIds.addAll(bankNode.getCurrencyUnitIds(nodeId));
		}
	}
	
	@Override
	public Data getData(UUID dataId) {
		return getData(dataId, DataImportance.MEDIUM);
	}	

	public Data getData(UUID dataId, DataImportance importance)
	{
		NodeGroup<DataProviderNode> providers = nodeManager.getDataProvidersForData(dataId);
		
		if(!providers.hasMoreNodes())
		{
			Simulation.getInstance().getStatistics().incrementFailedDataTransferCount();
			return null;
		}

		DataProviderNode provider = providers.getNode();

		Set<UUID> allocatedCurrencyUnitIds = 
			allocateCurrencyUnitsForTransaction(dataId, provider);

		CurrencyTransferAuthorization transferAuthorization = 
				new CurrencyTransferAuthorization(allocatedCurrencyUnitIds, getId(), provider.getId());
		
		try
		{			
			DataRequest request = new DataRequest(
				getId(), dataId, allocatedCurrencyUnitIds);
			
			Transaction transaction = provider.requestData(
				new SimulationSigned<DataRequest>(getId(), request));
			
			Data encryptedData = provider.getData(transaction);
			Data encryptedDataHash = hashCodec.getHash(encryptedData);			
						
			NodeGroup<BrokerNode> brokers = nodeManager.getBrokerNodes(
				transaction.getBrokerNodeIds());
			
			List<Data> decryptionKeyFragments = finalizeTransaction(
				brokers, transaction, transferAuthorization, encryptedDataHash);
			
			Data decryptionKey = sharedSecretCodec.decode(decryptionKeyFragments);
			Data decryptedData = encryptionCodec.decode(encryptedData, decryptionKey);
			
			dataStore.put(dataId, decryptedData);
			
			Simulation.getInstance().getStatistics().incrementDataTransferCount();
			
			return decryptedData;
		} catch(Throwable e)
		{
			returnCurrencyUnits(allocatedCurrencyUnitIds);
			return null;
		}
	}

	private List<Data> finalizeTransaction(NodeGroup<BrokerNode> brokers, 
			Transaction transaction, 
			CurrencyTransferAuthorization transferAuthorization,
			Data encryptedDataHash)
	{
		List<Data> decryptionKeyFragments = new ArrayList<Data>();

		while(brokers.hasMoreNodes())
		{
			for(BrokerNode broker : brokers.getNodes())
			{
				Data decryptionKeyFragment = broker.processTransaction(
					transaction, 
					new SimulationSigned<CurrencyTransferAuthorization>(getId(), transferAuthorization), 
					new SimulationSigned<Data>(getId(), encryptedDataHash));
				
				decryptionKeyFragments.add(decryptionKeyFragment);
			}
		}
		
		return decryptionKeyFragments;
	}
	
	private Set<UUID> allocateCurrencyUnitsForTransaction(UUID dataId, DataProviderNode provider)
	{
		Set<UUID> allocatedUnitIds = new HashSet<UUID>();
		
		Iterator<UUID> it = this.currencyUnitIds.iterator();
		if(it.hasNext())
		{		
			UUID currencyUnitId = it.next();
			
			allocatedUnitIds.add(currencyUnitId);
			it.remove();
		}

		return allocatedUnitIds;
	}
	
	private void returnCurrencyUnits(Set<UUID> allocatedUnitIds)
	{
		for(UUID currencyUnitId : allocatedUnitIds)
		{
			this.currencyUnitIds.add(currencyUnitId);
		}
	}
}
