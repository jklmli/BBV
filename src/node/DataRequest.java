package node;

import java.util.Set;
import java.util.UUID;

public class DataRequest
{
	private UUID consumerId;
	private UUID dataId;
	private Set<UUID> currencyIds;

	public UUID getConsumerId() {
		return consumerId;
	}
	public UUID getDataId() {
		return dataId;
	}
	public Set<UUID> getCurrencyIds() {
		return currencyIds;
	}
	
	public DataRequest(UUID consumerId, UUID dataId, Set<UUID> currencyIds) {
		super();
		this.consumerId = consumerId;
		this.dataId = dataId;
		this.currencyIds = currencyIds;
	}
}
