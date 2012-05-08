package simulation.node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import node.BankNode;
import node.BrokerNode;
import node.CurrencyTransferAuthorization;
import node.DataProviderNode;
import node.NodeManager;
import node.Transaction;
import util.Signed;
import data.Data;

public class SimulatedBrokerNode extends SimulatedNode implements BrokerNode {

	private Map<UUID, Set<UUID>> dataProviderMap = new HashMap<UUID, Set<UUID>>();
	private Map<UUID, Data> encryptedDataHashes = new HashMap<UUID, Data>();
	private Map<UUID, DecryptionKeySecretShare> decryptionKeyShares = 
		new HashMap<UUID, DecryptionKeySecretShare>();
	
	public SimulatedBrokerNode(UUID id, NodeManager nodeManager,
			Map<UUID, Data> dataStore) {
		super(id, nodeManager, dataStore);
	}

	@Override
	public void registerDataProvider(UUID nodeId, UUID dataId) {
		Set<UUID> providerIds = dataProviderMap.get(dataId);
		
		if(providerIds == null)
		{
			providerIds = new HashSet<UUID>();
			dataProviderMap.put(dataId, providerIds);
		}
		
		providerIds.add(nodeId);
	}

	@Override
	public void unregisterDataProvider(UUID nodeId, UUID dataId) {
		Set<UUID> providerIds = dataProviderMap.get(dataId);
		
		if(providerIds != null)
		{
			providerIds.remove(nodeId);
		}		
	}

	@Override
	public Set<UUID> getDataProviders(UUID dataId) {
		return dataProviderMap.get(dataId);
	}

	@Override
	public DecryptionKeySecretShare processTransaction(Transaction transaction,
			Signed<CurrencyTransferAuthorization> transferAuthorization,
			Signed<Data> encryptedDataHash) {
		
		UUID consumerId = transaction.getConsumerId();
		UUID dataId = transaction.getDataId();
		
		// Verify encrypted data hash
		
		Data expectedEncryptedDataHash = getEncryptedDataHash(dataId);
				
		if(!encryptedDataHash.equals(expectedEncryptedDataHash))
		{
			// TODO: Throw an exception
			return null;
		}
		
		// Verify currency transfer authorization
		
		int verifiedCount = 0;
		for(BankNode bankNode : nodeManager.getBankNodesForNode(consumerId).getNodes())
		{
			if(bankNode.verifyCurrencyTransfer(transferAuthorization))
			{
				verifiedCount++;
			}
		}
		
		if(verifiedCount < nodeManager.getBankNodeThreshold(consumerId))
		{
			// TODO: Throw an exception
			return null;
		}

		// Send signed copy of the transfer authorization to the provider
		
		transferAuthorization.sign(getId(), privateSigningKey);
		DataProviderNode provider = nodeManager.getDataProvider(transaction.getProviderId());		
		provider.addCurrencyTransferAuthorization(transaction.getId(), transferAuthorization);

		// Send the decryption key share to the consumer
		
		return getDecryptionKeyShare(dataId);
	}

	private Data getEncryptedDataHash(UUID dataId)
	{
		Data encryptedDataHash = encryptedDataHashes.get(dataId);
		
		if(encryptedDataHash == null)
		{
			// Retrieve encrypted data hash from other brokers or providers
		}

		return encryptedDataHash;
	}
	
	private DecryptionKeySecretShare getDecryptionKeyShare(UUID dataId)
	{
		DecryptionKeySecretShare decryptionKeyShare = decryptionKeyShares.get(dataId);
		
		if(decryptionKeyShare == null)
		{
			// Retrieve decryption key share from other brokers or providers
		}
		
		return decryptionKeyShare;
	}
}
