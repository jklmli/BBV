package simulation.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import node.BankNode;
import node.BrokerNode;
import node.CurrencyTransferAuthorization;
import node.DataProviderNode;
import node.DataRequest;
import node.NodeManager;
import node.NodeManager.NodeGroup;
import node.Transaction;
import simulation.util.SimulationSigned;
import util.Signed;
import data.Data;

public class SimulatedDataProviderNode extends SimulatedNode implements DataProviderNode {
	private static class TransactionContext
	{
		private Transaction transaction;
		private Signed<CurrencyTransferAuthorization> currencyTransferAuthorization;
		private List<BankNode> consumerBankNodes;

		public TransactionContext(
				Transaction transaction,
				Signed<CurrencyTransferAuthorization> currencyTransferAuthorization, 
				List<BankNode> consumerBankNodes) {
			super();
			this.transaction = transaction;
			this.currencyTransferAuthorization = currencyTransferAuthorization;
			this.consumerBankNodes = consumerBankNodes;
		}
		
		public Transaction getTransaction() {
			return transaction;
		}
		public Signed<CurrencyTransferAuthorization> getCurrencyTransferAuthorization() {
			return currencyTransferAuthorization;
		}		
		
		public void setCurrencyTransferAuthorization(Signed<CurrencyTransferAuthorization> transferAuth)
		{
			this.currencyTransferAuthorization = transferAuth;
		}
		
		public List<BankNode> getConsumerBankNodes()
		{
			return consumerBankNodes;
		}
	}
	
	private Map<UUID, TransactionContext> transactionContextMap = new HashMap<UUID, TransactionContext>();
	
	public SimulatedDataProviderNode(UUID nodeId, NodeManager nodeManager, Map<UUID, Data> dataStore)
	{
		super(nodeId, nodeManager, dataStore);
	}
	
	public Transaction requestData(Signed<DataRequest> signedDataRequest)
	{
		DataRequest dataRequest = signedDataRequest.getObject();
		
		UUID dataId = dataRequest.getDataId();
		UUID consumerId = dataRequest.getConsumerId();
			
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
				consumerId, getId(), dataId, dataRequest.getCurrencyIds(), brokerMap.keySet());

		List<BankNode> consumerBankNodes = 
			nodeManager.getBankNodesForNode(consumerId).getNodes();
		
		// Verify and hold currency units
		for(BankNode bankNode : consumerBankNodes)
		{
			bankNode.verifyAndHoldCurrencyUnits(signedDataRequest);
		}

		TransactionContext transactionContext = new TransactionContext(transaction, null, consumerBankNodes);
		transactionContextMap.put(transaction.getId(), transactionContext);
		
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
	public Data getGetDecryptionKeyShareForBroker(UUID dataId, UUID brokerId) {
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
	public void addCurrencyTransferAuthorization(
		UUID transactionId,
		Signed<CurrencyTransferAuthorization> currencyTransferAuthorization) {

		if(!currencyTransferAuthorization.isSignatureValid())
		{
			// TODO: Throw exception
		}
		
		TransactionContext transactionContext = transactionContextMap.get(transactionId);
		
		if(transactionContext == null)
		{
			// TODO: Throw exception
		}
		
		Signed<CurrencyTransferAuthorization> txnCurrencyTransferAuthorization = 
			transactionContext.getCurrencyTransferAuthorization();
		
		if(txnCurrencyTransferAuthorization == null)
		{
			txnCurrencyTransferAuthorization = currencyTransferAuthorization;

			transactionContext.setCurrencyTransferAuthorization(
				txnCurrencyTransferAuthorization);			
		} else
		{
			txnCurrencyTransferAuthorization.addSignatures(currencyTransferAuthorization);			
		}
		
		Transaction transaction = transactionContext.getTransaction();
		Set<UUID> brokerNodeIds = transaction.getBrokerNodeIds();
		int threshold = nodeManager.getBrokerThresholdForData(transaction.getDataId());
		
		if(txnCurrencyTransferAuthorization.getSigningNodeCount(brokerNodeIds) >=
			threshold)
		{
			// The transfer authorization has been signed by a threshold of brokers
			// forward it on to the consumer's bank nodes
			for(BankNode bankNode : transactionContext.getConsumerBankNodes())
			{
				bankNode.sendCurrency(
					new SimulationSigned<Transaction>(getId(), transaction), 
					txnCurrencyTransferAuthorization);
			}
			
			// TODO: Create signed transaction record / satisfaction statement 
			// and send to the appropriate bank nodes  
		}
	}

}
