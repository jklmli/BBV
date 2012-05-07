package node;

import java.util.List;

import util.Signed;
import data.Data;

public interface BrokerNode extends Node {
	
	public static class DecryptionKeySecretShare extends Data
	{
		public DecryptionKeySecretShare(byte[] data) {
			super(data);
		}
	}

	public List<DataProviderNode> getDataProviders();
	
	/**
	 * Register a transaction.  This method is called by data providers before
	 * data is provided.
	 */
	public void registerTransaction(Signed<Transaction> transaction);

	/**
	 * Verify that the encrypted data hash matches the expected value. If the
	 * hash matches forward a share of the payment authorization to the data 
	 * provider and return the decryption key share to the consumer.  This 
	 * method is called by data consumers after data is received. 
	 */
	public DecryptionKeySecretShare finalizeTransaction(
		Transaction transaction, 
		Signed<CurrencyTransferAuthorization> transferAuthorization, 
		Signed<Data> encryptedDataHash);	
}
