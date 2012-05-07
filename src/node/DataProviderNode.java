package node;

import java.util.UUID;

import util.Signed;
import data.Data;

public interface DataProviderNode extends Node {

	public Transaction requestData(Signed<DataRequest> dataRequest);
	public Data getData(Transaction transaction);
	
	public Data getEncryptedDataHash(UUID dataId);
	
	public Data getGetDecryptionKeyFragmentForBroker(UUID dataId, UUID brokerId);
	
	/**
	 * Adds a currency transfer authorization.  When the node receives 
	 * currency transfer authorizations from a sufficient number of brokers it 
	 * will forward it to the bank nodes 
	 */
	public void addCurrencyTransferAuthorization(
		UUID transactionId,
		Signed<CurrencyTransferAuthorization> currencyTransferAuthorization);
}
