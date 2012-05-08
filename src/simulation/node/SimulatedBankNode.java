package simulation.node;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import node.BankNode;
import node.CurrencyTransferAuthorization;
import node.CurrencyUnit;
import node.DataRequest;
import node.NodeManager;
import node.Transaction;
import node.TransactionRecord;
import util.Signed;
import data.Data;

public class SimulatedBankNode extends MemoryBankNode implements BankNode {

	public SimulatedBankNode(UUID id, NodeManager nodeManager,
			Map<UUID, Data> dataStore) {
		super(id, nodeManager, dataStore);
	}

	@Override
	public void registerNode(UUID nodeId, UUID bankSetId, Data publicSigningKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Data getPublicSigningKey(UUID nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyAndHoldCurrencyUnits(Signed<DataRequest> dataRequest) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verifyCurrencyTransfer(Signed<CurrencyTransferAuthorization> transferAuthorization) {
		if(!transferAuthorization.isSignatureValid())
		{
			return false;
		}
		
		// TODO Implement this
		return false;
	}

	@Override
	public void sendCurrency(Signed<Transaction> transaction,
			Signed<CurrencyTransferAuthorization> transferAuthorization) {
		
		if(!verifyCurrencyTransfer(transferAuthorization) || !transaction.isSignatureValid())
		{
			return;
		}
		
		transferAuthorization.sign(getId(), privateSigningKey);

		UUID fromNodeId = transferAuthorization.getObject().getTransferFromNodeId();
		UUID toNodeId = transferAuthorization.getObject().getTransferToNodeId();
		
		Set<CurrencyUnit> currencyUnits = getCurrencyUnits(
			fromNodeId, transferAuthorization.getObject().getCurrencyUnitIds());
		
		for(CurrencyUnit currencyUnit : currencyUnits)
		{
			currencyUnit.transfer(transferAuthorization);
		}
		
		for(BankNode toBankNode : nodeManager.getBankNodesForNode(toNodeId).getNodes())
		{
			toBankNode.receiveCurrency(transaction, currencyUnits);
		}
	}

	@Override
	public void receiveCurrency(Signed<Transaction> transaction,
			Set<CurrencyUnit> currencyUnits) {
		verifyCurrency(currencyUnits);
		add(currencyUnits);
	}

	@Override
	public void addTransactionRecord(Signed<TransactionRecord> record) {
		// TODO Auto-generated method stub		
	}

	@Override
	public List<Signed<TransactionRecord>> getRecentTransactionRecords(
			UUID nodeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void verifyCurrency(Set<CurrencyUnit> currencyUnits)
	{
		// TODO: Implement this
	}
}
