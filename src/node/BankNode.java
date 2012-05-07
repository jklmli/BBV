package node;

import java.util.Set;
import java.util.UUID;

import util.Signed;

public interface BankNode extends Node {
		
	public Set<UUID> getCurrencyUnitIds(UUID nodeId);
	
	public boolean verifyAndHoldCurrencyUnits(Signed<DataRequest> dataRequest);
	
	public void transferCurrency(
		Signed<Transaction> transaction,
		Signed<CurrencyTransferAuthorization> transferAuthorization);

}
