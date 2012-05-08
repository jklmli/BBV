package simulation.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import node.BankNode;
import node.BrokerNode;
import node.DataProviderNode;
import node.Node;
import node.NodeManager;
import simulation.SimulationNode;

public class MemoryNodeManager implements NodeManager {

	// TODO: Do not use fixed values for these
	private static final int SET_SIZE = 5;
	private static final int SET_THRESHOLD = 3;
	
	
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

	private static TreeMap<UUID, Node> nodes = new TreeMap<UUID, Node>();
		
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
		List<BrokerNode> brokers = getBrokerNodesForData(dataId).getNodes();

		for(BrokerNode broker : brokers)
		{
			broker.registerDataProvider(node.getId(), dataId);
		}
	}

	@Override
	public void unregisterFromProvidersForData(DataProviderNode dataProvider,
			UUID dataId) {
		List<BrokerNode> brokers = getBrokerNodesForData(dataId).getNodes();

		for(BrokerNode broker : brokers)
		{
			broker.unregisterDataProvider(node.getId(), dataId);
		}
	}

	@Override
	public NodeGroup<DataProviderNode> getDataProvidersForData(UUID dataId) {
		List<BrokerNode> brokers = getBrokerNodesForData(dataId).getNodes();

		Map<UUID, DataProviderNode> providers = new HashMap<UUID, DataProviderNode>();
		
		for(BrokerNode broker : brokers)
		{
			for(UUID providerId : broker.getDataProviders(dataId))
			{
				providers.put(providerId, getDataProvider(providerId));
			}
		}
		
		return new ListNodeGroup<DataProviderNode>(
			new ArrayList<DataProviderNode>(providers.values()));
	}
	
	@Override
	public DataProviderNode getDataProvider(UUID providerId) {
		return (DataProviderNode) nodes.get(providerId);
	}

	@Override
	public NodeGroup<BankNode> getBankNodesForNode(UUID nodeId) {
		List<BankNode> bankNodes = new ArrayList<BankNode>();
		for(Node node : getNodesNearId(nodeId, SET_SIZE))
		{
			bankNodes.add((BankNode) node);
		}
		
		return new ListNodeGroup<BankNode>(bankNodes);
	}
	
	@Override
	public int getBankNodeThreshold(UUID nodeId) {
		return SET_THRESHOLD;
	}

	@Override
	public NodeGroup<BrokerNode> getBrokerNodesForData(UUID dataId) {
		List<BrokerNode> brokers = new ArrayList<BrokerNode>();
		for(Node node : getNodesNearId(dataId, SET_SIZE))
		{
			brokers.add((BrokerNode) node);
		}
		
		return new ListNodeGroup<BrokerNode>(brokers);
	}

	@Override
	public NodeGroup<BrokerNode> getBrokerNodes(Set<UUID> brokerNodeIds) {
		List<BrokerNode> brokers = new ArrayList<BrokerNode>();
		
		for(UUID brokerId : brokerNodeIds)
		{
			BrokerNode broker = (BrokerNode) nodes.get(brokerId);
			
			if(broker != null)
			{
				brokers.add(broker);
			}
		}
		
		return new ListNodeGroup<BrokerNode>(brokers);
	}

	@Override
	public int getBrokerThresholdForData(UUID dataId) {
		return SET_THRESHOLD;
	}
	
	public List<Node> getNodesNearId(UUID id, int count)
	{		
		// TODO: Implement this
		return null;
	}
}
