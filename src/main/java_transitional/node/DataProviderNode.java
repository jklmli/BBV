package node;

import java.util.UUID;

import main.scala.data.Data;
import node.BrokerNode.Transaction;

public interface DataProviderNode extends Node {

	public Transaction requestData(UUID consumerId, UUID dataId, int paymentAmount);
	public Data getData(Transaction transaction);
	
	public Data getEncryptedDataHash(UUID dataId);
	
	public Data getGetDecryptionKeyFragmentForBroker(UUID dataId, UUID brokerId);
	
	/**
	 * Adds a currency transfer authorization fragment.  When the node receives 
	 * a sufficient number of currency transfer authorization fragments it will 
	 * be able to restore the transfer authorization and forward it to the bank 
	 * nodes 
	 */
	public void addCurrencyTransferAuthorizationFragment(Data currencyTransferFragment);
}
