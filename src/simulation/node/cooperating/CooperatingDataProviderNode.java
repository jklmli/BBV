package simulation.node.cooperating;

import java.util.Map;
import java.util.UUID;

import node.DataProviderNode;
import node.NodeManager;
import data.Data;

public class CooperatingDataProviderNode implements DataProviderNode {

	private UUID nodeId;
	private NodeManager nodeManager;
	private Map<UUID, Data> dataStore;
	
	
	public CooperatingDataProviderNode(UUID nodeId, NodeManager nodeManager, Map<UUID, Data> dataStore)
	{
		this.nodeId = nodeId;
		this.nodeManager = nodeManager;
		this.dataStore = dataStore;
	}
	
	@Override
	public UUID getId() {
		return nodeId;
	}

	@Override
	public Data getData(UUID consumerNodeId, UUID dataId,
			int currencyUnitsOffered) {
		return dataStore.get(dataId);
	}
}
