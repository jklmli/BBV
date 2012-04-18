package simulation.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import node.BankNode;
import node.BrokerNode;
import node.DataProviderNode;
import node.Node;
import node.NodeManager;
import simulation.SimulationNode;

public class MemoryNodeManager implements NodeManager {

	public static class ListNodeGroup<T extends Node> implements NodeGroup<T>
	{
		private final List<T> nodes;
		
		public ListNodeGroup(List<T> nodes)
		{
			this.nodes = (nodes != null) ? new ArrayList<T>(nodes) : new ArrayList<T>();
		}
		
		@Override
		public List<T> getNodes() {
			return getNodes(nodes.size());
		}

		@Override
		public List<T> getNodes(int count) {
			List<T> retVal = new ArrayList<T>();
			
			for(int cnt = 1; cnt <= count; cnt++)
			{
				T node = nodes.remove(0);
				if(node != null)
				{
					retVal.add(node);
				}
			}
			
			return retVal;
		}

		@Override
		public boolean hasMoreNodes() {
			return nodes.size() > 0;
		}

		@Override
		public T getNode() {
			if(!hasMoreNodes())
			{
				return null;
			}
			return getNodes(1).get(0);
		}
	}

	private static Map<UUID, Node> nodes = new HashMap<UUID, Node>();
	private static Map<UUID, List<DataProviderNode>> dataProviderMap = 
		new HashMap<UUID, List<DataProviderNode>>();
	
	private SimulationNode node;
	
	@Override
	public SimulationNode addNode() {
		return new SimulationNode(UUID.randomUUID());
	}

	@Override
	public void removeNode() {
		nodes.remove(node.getId());
	}

	@Override
	public void connect(Node node) {
		this.node = (SimulationNode) node;
	}

	@Override
	public void disconnect() {
		this.node = null;
	}

	@Override
	public void registerAsProviderForData(UUID dataId) {
		List<DataProviderNode> providers = dataProviderMap.get(dataId);
		if(providers == null)
		{
			providers = new ArrayList<DataProviderNode>();
			dataProviderMap.put(dataId, providers);
		}
		
		providers.add(node.getDataProviderNodeBehavior());
	}

	@Override
	public void unregisterFromProvidersForData(DataProviderNode dataProvider,
			UUID dataId) {
		List<DataProviderNode> providers = dataProviderMap.get(dataId);
		if(providers != null)
		{
			providers.remove(dataProvider);
		}		
	}

	@Override
	public NodeGroup<DataProviderNode> getDataProvidersForData(UUID dataId) {
		return new ListNodeGroup<DataProviderNode>(dataProviderMap.get(dataId));
	}

	@Override
	public NodeGroup<BankNode> getBankNodesForNode(UUID nodeId) {
		return null;
	}

	@Override
	public NodeGroup<BrokerNode> getBrokerNodesForData(UUID dataId) {
		return null;
	}

	@Override
	public NodeGroup<BrokerNode> getBrokerNodes(Set<UUID> brokerNodeIds) {
		return null;
	}
}
