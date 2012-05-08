package simulation.node;

import java.util.Map;
import java.util.UUID;

import node.Node;
import node.NodeManager;
import data.Data;
import data.HashCodec;
import data.SharedSecretCodec;
import data.SymmetricEncryptionCodec;

public abstract class SimulatedNode implements Node {

	protected UUID id;
	protected NodeManager nodeManager;
	protected Map<UUID, Data> dataStore;
		
	// TODO: Initialize these
	protected final SymmetricEncryptionCodec encryptionCodec = null;
	protected final HashCodec hashCodec = null;
	protected final SharedSecretCodec sharedSecretCodec = null;
	protected Data privateSigningKey;

	
	public SimulatedNode(UUID id, NodeManager nodeManager, Map<UUID, Data> dataStore)
	{		
		this.id = id;
		this.nodeManager = nodeManager;
		this.dataStore = dataStore;
	}
	
	@Override
	public UUID getId() {
		return id;
	}

}
