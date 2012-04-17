package simulation.node;

import java.util.ArrayList;
import java.util.HashMap;
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
import data.Data;

public class SimulatedDataConsumerNode extends SimulatedNode implements DataConsumerNode {

	private final List<BankNode> bankNodes = new ArrayList<BankNode>();
	
	private final Map<UUID, CurrencyUnit> currencyMap = new HashMap<UUID, CurrencyUnit>();
	
	
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
			for(CurrencyUnit currencyUnit : bankNode.getCurrencyUnits(nodeId))
			{
				if(currencyUnit.isValid() && currencyUnit.getOwnerId().equals(nodeId))
				{
					currencyMap.put(currencyUnit.getId(), currencyUnit);
				}
			}
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
				
		try
		{
			Data encryptedData = provider.getData(getId(), dataId, currencyUnits.size());
			Data encryptedDataHash = hashCodec.getHash(encryptedData);
			
			List<Data> decryptionKeyFragments = new ArrayList<Data>();
			
			// TODO: get provider token
			Data providerToken = null; 
			
			Transaction transaction = new Transaction(
				getId(), provider.getId(), providerToken, currencyUnits, encryptedDataHash);
			
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
		Set<CurrencyUnit> currencyUnits = new HashSet<CurrencyUnit>();
		
		List<CurrencyUnit> values = new ArrayList<CurrencyUnit>(currencyMap.values());
		
		if(values.size() > 0)
		{
			CurrencyUnit currencyUnit = values.get(0);
			currencyUnit.transferTo(provider.getId());
			currencyUnits.add(currencyUnit);
			currencyMap.remove(currencyUnit.getId());
		}

		return currencyUnits;
	}
	
	private void returnCurrencyUnits(Set<CurrencyUnit> currencyUnits)
	{
		for(CurrencyUnit currencyUnit : currencyUnits)
		{
			currencyUnit.transferTo(getId());
			currencyMap.put(currencyUnit.getId(), currencyUnit);
		}
	}
}
