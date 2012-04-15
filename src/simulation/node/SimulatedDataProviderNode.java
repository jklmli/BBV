package simulation.node;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import node.DataProviderNode;
import node.NodeManager;
import data.Data;

public class SimulatedDataProviderNode extends SimulatedNode implements DataProviderNode {
	
	public SimulatedDataProviderNode(UUID nodeId, NodeManager nodeManager, Map<UUID, Data> dataStore)
	{
		super(nodeId, nodeManager, dataStore);
	}
	
	@Override
	public Data getData(UUID consumerNodeId, UUID dataId,
			int currencyUnitsOffered) {
		return dataStore.get(dataId);
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
}
