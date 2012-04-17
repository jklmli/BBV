package node;

import java.util.UUID;

public class CurrencyUnit {

	private UUID id;
	private UUID ownerId;

	public UUID getId() {
		return id;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void transferTo(UUID ownerId)
	{
		this.ownerId = ownerId;
	}

	public boolean isValid() {
		return true;
	}
}
