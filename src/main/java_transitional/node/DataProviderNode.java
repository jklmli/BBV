package node;

import java.util.UUID;

import main.scala.data.Data;

public interface DataProviderNode extends Node {

	public Data getData(UUID consumerNodeId, UUID dataId, int currencyUnitsOffered);

	public Data getEncryptedDataHash(UUID dataId);
	
	public Data getGetDecryptionKeyFragmentForBroker(UUID dataId, UUID brokerId);
}
