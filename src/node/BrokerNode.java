package edu.uiuc.cs598.project.bbv.node;

import java.util.Set;
import java.util.UUID;

import edu.uiuc.cs598.project.bbv.CurrencyUnit;
import edu.uiuc.cs598.project.bbv.data.Data;

public interface BrokerNode extends Node {

	public static class Transaction
	{
		private UUID consumerId;
		private UUID providerId;
		
		private Data providerToken;
		private Set<CurrencyUnit> payment;
		private Data encryptedDataHash;
	}
	
	public static class DataProviderToken
	{
		private UUID providerId;
		private int expectedPaymentAmount;
	}
	
	
	public static class DecryptionKeySecretShare extends Data
	{
		public DecryptionKeySecretShare(byte[] data) {
			super(data);
		}
	}
	
	// Decode data provider token
	
	// Verify that the data in the provider token matches the transaction 
	// information provided by the consumer
	
	
	// Verify that the encrypted data hash matches the expected value
	
	
	// For each bank node
	
	// --- Submit the payment information to the bank node
	
	// Send 
	// Return the data decryption key
	
	public DecryptionKeySecretShare finalizeTransaction(Transaction transaction);	
}
