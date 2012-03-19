package edu.uiuc.cs598.project.bbv.node;

import java.util.List;
import java.util.UUID;

import edu.uiuc.cs598.project.bbv.CurrencyUnit;

public interface BankNode extends Node {

	public void add(List<CurrencyUnit> currencyUnits);
	
	public void remove(List<CurrencyUnit> currencyUnits);
	
	public List<CurrencyUnit> getCurrencyUnits(UUID nodeId, List<UUID> ids);
	
}
