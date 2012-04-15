package node;

import java.util.Set;
import java.util.UUID;

import data.Data;

public interface BrokerNode extends Node {

	public static class Transaction
	{
		private UUID consumerId;
		private UUID providerId;
		
		private Data providerToken;
		private Set<CurrencyUnit> payment;
		private Data encryptedDataHash;
		
		public Transaction(UUID consumerId, UUID providerId,
				Data providerToken, Set<CurrencyUnit> payment,
				Data encryptedDataHash) {
			super();
			this.consumerId = consumerId;
			this.providerId = providerId;
			this.providerToken = providerToken;
			this.payment = payment;
			this.encryptedDataHash = encryptedDataHash;
		}
		
		public UUID getConsumerId() {
			return consumerId;
		}

		public UUID getProviderId() {
			return providerId;
		}

		public Data getProviderToken() {
			return providerToken;
		}

		public Set<CurrencyUnit> getPayment() {
			return payment;
		}

		public Data getEncryptedDataHash() {
			return encryptedDataHash;
		}		
	}
	
	public static class DataProviderToken
	{
		private UUID providerId;
		private int expectedPaymentAmount;

		public DataProviderToken(UUID providerId, int expectedPaymentAmount) {
			super();
			this.providerId = providerId;
			this.expectedPaymentAmount = expectedPaymentAmount;
		}

		public UUID getProviderId() {
			return providerId;
		}

		public int getExpectedPaymentAmount() {
			return expectedPaymentAmount;
		}
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
