package simulation.node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import node.BankNode;
import node.CurrencyUnit;
import node.NodeManager;
import data.Data;

public abstract class MemoryBankNode extends SimulatedNode implements BankNode {

	private static class CurrencyMap extends HashMap<UUID, CurrencyUnit> {
		private static final long serialVersionUID = 1L;
	}

	private Map<UUID, CurrencyMap> nodeCurrencyMap = new HashMap<UUID, CurrencyMap>();

	public MemoryBankNode(UUID id, NodeManager nodeManager,
			Map<UUID, Data> dataStore) {
		super(id, nodeManager, dataStore);
	}

	public void add(Set<CurrencyUnit> currencyUnits) {
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

	public void remove(Set<CurrencyUnit> currencyUnits) {
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
	
	protected Set<CurrencyUnit> getCurrencyUnits(UUID nodeId, Set<UUID> currencyUnitIds)
	{
		Set<CurrencyUnit> currencyUnits = new HashSet<CurrencyUnit>();
		
		CurrencyMap currencyMap = nodeCurrencyMap.get(nodeId);
		
		if(currencyMap == null)
		{
			return null;
		}
		
		for(UUID currencyUnitId : currencyUnitIds)
		{
			CurrencyUnit currencyUnit = currencyMap.get(currencyUnitId);
			if(currencyUnit != null)
			{
				currencyUnits.add(currencyUnit);
			}
		}
		
		return currencyUnits;
	}
}
