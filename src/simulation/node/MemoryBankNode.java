package simulation.node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import node.BankNode;
import node.CurrencyUnit;

public abstract class MemoryBankNode implements BankNode {

	private static class CurrencyMap extends HashMap<UUID, CurrencyUnit> {
		private static final long serialVersionUID = 1L;
	}

	private UUID id;
	private Map<UUID, CurrencyMap> nodeCurrencyMap = new HashMap<UUID, CurrencyMap>();

	public MemoryBankNode() {
		id = UUID.randomUUID();
	}

	@Override
	public UUID getId() {
		return id;
	}

	public void add(List<CurrencyUnit> currencyUnits) {
		for (CurrencyUnit currencyUnit : currencyUnits) {
			CurrencyMap currencyMap = nodeCurrencyMap.get(currencyUnit
					.getOwnerId());

			if (currencyMap == null) {
				currencyMap = new CurrencyMap();
				nodeCurrencyMap.put(currencyUnit.getOwnerId(), currencyMap);
			}

			currencyMap.put(currencyUnit.getId(), currencyUnit);
		}
	}

	public void remove(List<CurrencyUnit> currencyUnits) {
		for (CurrencyUnit currencyUnit : currencyUnits) {
			CurrencyMap currencyMap = nodeCurrencyMap.get(currencyUnit
					.getOwnerId());

			if (currencyMap != null) {
				currencyMap.remove(currencyUnit.getId());
			}
		}
	}

	@Override
	public Set<UUID> getCurrencyUnitIds(UUID nodeId) {		
		CurrencyMap currencyMap = nodeCurrencyMap.get(nodeId);
		
		if(currencyMap == null)
		{
			return new HashSet<UUID>();
		}
		
		return currencyMap.keySet();
	}
}
