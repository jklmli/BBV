package simulation.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import util.Signed;
import data.Data;

public class SimulationSigned<T> implements util.Signed<T> {
	private T object;
	
	private Set<UUID> signingNodeIds = new HashSet<UUID>();
	
	public SimulationSigned(UUID signingNodeId, T object)
	{
		this.signingNodeIds.add(signingNodeId);
		this.object = object;
	}
	
	public SimulationSigned(Set<UUID> signingNodeIds, T object)
	{
		this.signingNodeIds = signingNodeIds;
		this.object = object;
	}
	
	public Set<UUID> getSigningNodeIds()
	{
		return signingNodeIds;
	}

	public T getObject()
	{
		return object;
	}
	
	public boolean isSignatureValid()
	{
		return true;
	}
	
	public void addSignature(UUID signingNodeId)
	{
		signingNodeIds.add(signingNodeId);
	}

	@Override
	public Data getData() {
		// TODO: Implement this method
		return null;
	}
	
	public void addSignatures(Signed<T> signedObject)
	{
		if(getData().equals(signedObject.getData()))
		{
			// TODO: Throw exception
		}

		signingNodeIds.addAll(signedObject.getSigningNodeIds());		
	}

	@Override
	public int getSigningNodeCount(Set<UUID> nodeIds) {
		int signingNodeCount = 0;
		
		for(UUID nodeId : nodeIds)
		{
			if(signingNodeIds.contains(nodeId))
			{
				signingNodeCount++;
			}
		}

		return signingNodeCount;
	}

	@Override
	public void sign(UUID id, Data privateSigningKey) {
		signingNodeIds.add(id);
	}
}
