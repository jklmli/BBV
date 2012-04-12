package simulation.node;

import java.util.Map;
import java.util.UUID;

import node.DataConsumerNode;
import node.DataProviderNode;
import node.NodeManager;
import node.NodeManager.NodeGroup;
import simulation.Simulation;
import data.Data;

public class SimulatedDataConsumerNode implements DataConsumerNode {

	private final UUID nodeId;
	private final NodeManager nodeManager;
	private final Map<UUID, Data> dataStore;
	
	public SimulatedDataConsumerNode(UUID nodeId, NodeManager nodeManager, Map<UUID, Data> dataStore)
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
	public Data getData(UUID dataId) {
		return getData(dataId, DataImportance.MEDIUM);
	}	

	public Data getData(UUID dataId, DataImportance importance)
	{
		NodeGroup<DataProviderNode> providers = nodeManager.getDataProvidersForData(dataId);
		
		if(!providers.hasMoreNodes())
		{
			Simulation.getInstance().getStatistics().incrementFailedDataTransferCount();
			return null;
		}

		DataProviderNode provider = providers.getNode();
		Data data = provider.getData(getId(), dataId, 0);
		dataStore.put(dataId, data);
		
		Simulation.getInstance().getStatistics().incrementDataTransferCount();
		
		return data;		
	}

}
