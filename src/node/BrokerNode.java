package node;

import java.util.Set;
import java.util.UUID;

import util.Signed;
import data.Data;

public interface BrokerNode extends Node {
	
	/**
	 * Represents a share of the decryption key
	 */
	public static class DecryptionKeySecretShare extends Data
	{
		public DecryptionKeySecretShare(byte[] data) {
			super(data);
		}
	}

	/**
	 * Register a node as a data provider for the given data id
	 */
	public void registerDataProvider(UUID nodeId, UUID dataId);
	
	/**
	 * Unregister a node as a data provider for the given data id
	 */
	public void unregisterDataProvider(UUID nodeId, UUID dataId);
	
	/**
	 * Returns a set of data providers for the given data id
	 */
	public Set<UUID> getDataProviders(UUID dataId);
	
	/**
	 * Verify that the encrypted data hash matches the expected value. If the
	 * hash matches forward a signed copy of the payment authorization to the data 
	 * provider and return the decryption key share to the consumer.  This 
	 * method is called by data consumers after data is received. 
	 */
	public DecryptionKeySecretShare processTransaction(
		Transaction transaction, 
		Signed<CurrencyTransferAuthorization> transferAuthorization, 
		Signed<Data> encryptedDataHash);

}
