package simulation.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import node.BrokerNode.Transaction;
import node.NodeManager.NodeGroup;
import node.BrokerNode;
import node.DataProviderNode;
import node.NodeManager;
import data.Data;

public class SimulatedDataProviderNode extends SimulatedNode implements DataProviderNode {
	
	public SimulatedDataProviderNode(UUID nodeId, NodeManager nodeManager, Map<UUID, Data> dataStore)
	{
		super(nodeId, nodeManager, dataStore);
	}
	
	public Transaction requestData(UUID consumerId, UUID dataId, int paymentAmount)
	{
		// Select brokers
		Map<UUID, BrokerNode> brokerMap = new HashMap<UUID, BrokerNode>();
		NodeGroup<BrokerNode> brokers = nodeManager.getBrokerNodesForData(dataId);
		while(brokers.hasMoreNodes())
		{
			for(BrokerNode broker : brokers.getNodes())
			{
				brokerMap.put(broker.getId(), broker);
			}
		}
		
		Transaction transaction = new Transaction(
			consumerId, getId(), dataId, paymentAmount, brokerMap.keySet());

		// Register transaction with brokers
		for(BrokerNode broker : brokerMap.values())
		{
			broker.registerTransaction(transaction);
		}

		return transaction;
	}
	
	@Override
	public Data getData(Transaction transaction) {
		return dataStore.get(transaction.getDataId());
	}

	@Override
	public Data getEncryptedDataHash(UUID dataId) {
		Data data = dataStore.get(dataId);
		Data encryptedData =  encryptionCodec.encode(data, getEncryptionKeyForData(dataId)); 
		return hashCodec.getHash(encryptedData);
	}

	@Override
	public Data getGetDecryptionKeyFragmentForBroker(UUID dataId, UUID brokerId) {
		Data encryptionKey = getEncryptionKeyForData(dataId);
		List<Data> decryptionKeyFragments = sharedSecretCodec.encode(encryptionKey);
		
		// Index is based on a hash code of the broker id 
		int idx = (int) ((brokerId.getLeastSignificantBits() + brokerId.getMostSignificantBits()) % decryptionKeyFragments.size());
		return decryptionKeyFragments.get(idx);
	}
	
	private Data getEncryptionKeyForData(UUID dataId)
	{
		Data data = dataStore.get(dataId);
		return hashCodec.getHash(data);
	}

	@Override
	public void addCurrencyTransferAuthorizationFragment(
			Data currencyTransferFragment) {
	}
}
