package node;

import java.util.List;
import java.util.UUID;

public interface BankNode extends Node {

	public void add(List<CurrencyUnit> currencyUnits);
	
	public void remove(List<CurrencyUnit> currencyUnits);
	
	public List<CurrencyUnit> getCurrencyUnits(UUID nodeId, List<UUID> ids);
	
}
