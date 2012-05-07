package node;

import java.util.Set;
import java.util.UUID;

public class Transaction
{
	private UUID id;
	private UUID consumerId;
	private UUID providerId;
	private UUID dataId;
	private Set<UUID> paymentCurrencyUnitIds;
	private Set<UUID> brokerNodeIds;
	
	public Transaction(
			UUID consumerId, 
			UUID providerId, 
			UUID dataId, 
			Set<UUID> paymentCurrencyUnitIds, 
			Set<UUID> brokerNodeIds) {
		super();
		this.id = UUID.randomUUID();
		this.consumerId = consumerId;
		this.providerId = providerId;
		this.dataId = dataId;
		this.paymentCurrencyUnitIds = paymentCurrencyUnitIds;
		this.brokerNodeIds = brokerNodeIds;
	}
	
	public UUID getId()
	{
		return id;
	}
	
	public UUID getConsumerId() {
		return consumerId;
	}

	public UUID getProviderId() {
		return providerId;
	}

	public UUID getDataId() {
		return dataId;
	}

	public Set<UUID> getPaymentCurrencyUnitIds() {
		return paymentCurrencyUnitIds;
	}
	
	public Set<UUID> getBrokerNodeIds()
	{
		return brokerNodeIds;
	}
}
