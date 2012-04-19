package simulation.node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import node.BankNode;
import node.BrokerNode;
import node.BrokerNode.Transaction;
import node.CurrencyUnit;
import node.DataConsumerNode;
import node.DataProviderNode;
import node.NodeManager;
import node.NodeManager.NodeGroup;
import simulation.Simulation;
import main.scala.data.Data;

public class SimulatedDataConsumerNode extends SimulatedNode implements DataConsumerNode {

	private final List<BankNode> bankNodes = new ArrayList<BankNode>();
		
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

		Set<CurrencyUnit> currencyUnits = allocateCurrencyUnitsForTransaction(dataId, provider);
		
		for(CurrencyUnit currencyUnit : currencyUnits)
		{
			currencyUnit.transferTo(provider.id());
		}
		
		try
		{
			Data encryptedData = provider.getData(id(), dataId, currencyUnits.size());
			Data encryptedDataHash = hashCodec.getHash(encryptedData);
			
			List<Data> decryptionKeyFragments = new ArrayList<Data>();
			
			// TODO: get provider token
			Data providerToken = null; 
			
			Transaction transaction = new Transaction(
				id(), provider.id(), providerToken, currencyUnits, encryptedDataHash);
			
			NodeGroup<BrokerNode> brokers = nodeManager.getBrokerNodesForData(dataId);
			while(brokers.hasMoreNodes())
			{
				for(BrokerNode broker : brokers.getNodes())
				{
					Data decryptionKeyFragment = broker.finalizeTransaction(transaction);
					decryptionKeyFragments.add(decryptionKeyFragment);
				}
			}
			
			Data decryptionKey = sharedSecretCodec.decode(decryptionKeyFragments);
			Data decryptedData = encryptionCodec.decode(encryptedData, decryptionKey);
			
			dataStore.put(dataId, decryptedData);
			
			Simulation.getInstance().getStatistics().incrementDataTransferCount();
			
			return decryptedData;
		} catch(Throwable e)
		{
			returnCurrencyUnits(currencyUnits);
			return null;
		}
	}

	private Set<CurrencyUnit> allocateCurrencyUnitsForTransaction(UUID dataId, DataProviderNode provider)
	{
		// TODO: Implement this
		Set<CurrencyUnit> currencyUnits = new HashSet<CurrencyUnit>();		
		return currencyUnits;
	}
	
	private void returnCurrencyUnits(Set<CurrencyUnit> currencyUnits)
	{
		// TODO: Implement this
		
		for(CurrencyUnit currencyUnit : currencyUnits)
		{
			currencyUnit.transferTo(id());
		}
	}
}
