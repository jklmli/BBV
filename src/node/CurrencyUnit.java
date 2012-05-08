package node;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import util.Signed;

/**
 * Represents a unit of currency.  A currency unit consists of a creation
 * authorization and a chain of transfer authorizations
 */
public class CurrencyUnit {

	private UUID id;
	private UUID ownerId;
	
	// TODO: Add creation authorization
	
	private List<Signed<CurrencyTransferAuthorization>> transferAuthorizations 
		= new ArrayList<Signed<CurrencyTransferAuthorization>>();

	public UUID getId() {
		return id;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public boolean isValid() {
		return true;
	}
	
	public void transfer(Signed<CurrencyTransferAuthorization> transferAuthorization)
	{
		if(!transferAuthorization.isSignatureValid())
		{
			return;
		}
		
		transferAuthorizations.add(transferAuthorization);
		ownerId = transferAuthorization.getObject().getTransferToNodeId();
	}
}
