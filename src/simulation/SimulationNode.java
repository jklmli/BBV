package simulation;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import node.BankNode;
import node.BrokerNode;
import node.DataConsumerNode;
import node.DataProviderNode;
import node.Node;
import node.NodeManager;
import simulation.node.MemoryNodeManager;
import simulation.node.SimulatedBankNode;
import simulation.node.SimulatedBrokerNode;
import simulation.node.SimulatedDataConsumerNode;
import simulation.node.SimulatedDataProviderNode;
import data.Data;

public class SimulationNode implements Node {

	private final UUID id;

	private final DataConsumerNode consumerNodeBehavior;
	private final DataProviderNode providerNodeBehavior;
	private final BrokerNode brokerNodeBehavior;
	private final BankNode bankNodeBehavior;
	
	private final Map<UUID, Data> dataStore = new LinkedHashMap<UUID, Data>();
	
	private final NodeManager nodeManager = new MemoryNodeManager();

	
	public SimulationNode(UUID id)
	{		
		this.id = id;
		
		nodeManager.connect(this);
		
		consumerNodeBehavior = new SimulatedDataConsumerNode(id, nodeManager, dataStore);
		providerNodeBehavior = new SimulatedDataProviderNode(id, nodeManager, dataStore);
		brokerNodeBehavior = new SimulatedBrokerNode(id, nodeManager, dataStore);
		bankNodeBehavior = new SimulatedBankNode(id, nodeManager, dataStore);
	}
	
	@Override
	public UUID getId() {
		return id;
	}

	public void performOperation()
	{
		Set<UUID> dataIds = new HashSet<UUID>(Simulation.getInstance().getDataIds());
		dataIds.removeAll(dataStore.keySet());
		
		if(dataIds.size() == 0)
		{
			return;
		}
		
		UUID dataId = dataIds.iterator().next();
		consumerNodeBehavior.getData(dataId);
	}
	
	public void destroy()
	{
		nodeManager.disconnect();
		nodeManager.removeNode();
	}

	public void addData(UUID dataId, Data data)
	{
		dataStore.put(dataId, data);
		nodeManager.registerAsProviderForData(dataId);
	}

	public DataProviderNode getDataProviderNodeBehavior() {
		return providerNodeBehavior;
	}
}