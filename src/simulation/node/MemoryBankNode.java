package simulation.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import node.BankNode;
import node.CurrencyUnit;

public class MemoryBankNode implements BankNode {

	private static class CurrencyMap extends HashMap<UUID, CurrencyUnit> {
		private static final long serialVersionUID = 1L;
	}

	private UUID id;
	private Map<UUID, CurrencyMap> nodeCurrencyMap = new HashMap<UUID, CurrencyMap>();

	public MemoryBankNode() {
		id = UUID.randomUUID();
	}

	@Override
	public UUID id() {
		return id;
	}

	@Override
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

	@Override
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
	public List<CurrencyUnit> getCurrencyUnits(UUID nodeId, List<UUID> ids) {
		CurrencyMap currencyMap = nodeCurrencyMap.get(nodeId);

		if (currencyMap == null) {
			return new ArrayList<CurrencyUnit>();
		}

		List<CurrencyUnit> currencyUnits = new ArrayList<CurrencyUnit>();

		for (UUID id : ids) {
			CurrencyUnit currencyUnit = currencyMap.get(id);

			if (currencyUnit != null) {
				currencyUnits.add(currencyUnit);
			}
		}

		return currencyUnits;
	}
}
