package node;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import util.Signed;
import data.Data;

public interface BankNode extends Node {
	
	
	/**
	 * Register a new node
	 */
	public void registerNode(UUID nodeId, UUID bankSetId, Data publicSigningKey);

	/**
	 * Get the public signing key for a node
	 */
	public Data getPublicSigningKey(UUID nodeId);

	/**
	 * Get the ids of the currency units owned by a node
	 */
	public Set<UUID> getCurrencyUnitIds(UUID nodeId);

	/**
	 * Verify and place a hold on the currency units specified in the given
	 * data request
	 */
	public boolean verifyAndHoldCurrencyUnits(Signed<DataRequest> dataRequest);

	/**
	 * Verify a currency transfer authorization
	 */
	public boolean verifyCurrencyTransfer(Signed<CurrencyTransferAuthorization> transferAuthorization);

	/**
	 * Initiate a currency transfer.  The transfer authorization must be signed
	 * by the node owning the currency as well as a threshold of brokers for the
	 * data specified in the given transaction
	 */
	public void sendCurrency(
		Signed<Transaction> transaction,
		Signed<CurrencyTransferAuthorization> transferAuthorization);

	public void receiveCurrency(
		Signed<Transaction> transaction,
		Set<CurrencyUnit> currencyUnits);
	
	/**
	 * Add a transaction record
	 */
	public void addTransactionRecord(Signed<TransactionRecord> record);

	/**
	 * Returns a list of recent transaction records for the given node
	 */
	public List<Signed<TransactionRecord>> getRecentTransactionRecords(UUID nodeId);
	
}
