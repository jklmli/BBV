package node;

import java.util.Set;
import java.util.UUID;

public class CurrencyTransferAuthorization
{
	private Set<UUID> currencyUnitIds;
	
	private UUID transferFromNodeId;
	private UUID transferToNodeId;

	public CurrencyTransferAuthorization(Set<UUID> currencyUnitIds,
			UUID transferFromNodeId, UUID transferToNodeId) {
		super();
		this.currencyUnitIds = currencyUnitIds;
		this.transferFromNodeId = transferFromNodeId;
		this.transferToNodeId = transferToNodeId;
	}
	
	public Set<UUID> getCurrencyUnitIds() {
		return currencyUnitIds;
	}
	public UUID getTransferFromNodeId() {
		return transferFromNodeId;
	}
	public UUID getTransferToNodeId() {
		return transferToNodeId;
	}
}
