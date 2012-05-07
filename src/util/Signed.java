package util;

import java.util.Set;
import java.util.UUID;

import data.Data;

public interface Signed<T> {
	public Set<UUID> getSigningNodeIds();
	public T getObject();	
	public boolean isSignatureValid();	
	public Data getData();
	
	public void addSignatures(Signed<T> signedObject);
	public int getSigningNodeCount(Set<UUID> nodeIds);
}
