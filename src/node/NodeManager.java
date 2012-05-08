package node;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a node's connection to the network
 */

public interface NodeManager {

	/**
	 * Represents a group of nodes.
	 */
	public interface NodeGroup<T extends Node>
	{
		/**
		 * Attempts to return some number of nodes from the group.  Each call to 
		 * getNodes may return additional nodes while hasMoreNodes returns true
		 */
		public List<T> getNodes();

		/**
		 * Attempts to return up to count nodes in the group.  Each call to 
		 * getNodes may return additional nodes while hasMoreNodes returns true
		 */
		public List<T> getNodes(int count);
		
		/**
		 * Returns true when there may be more nodes in the group
		 */
		public boolean hasMoreNodes();

		public T getNode();
	}
	
	/**
	 * Adds a new node to the network.  This method will establish an 
	 * identity (id, public key, private key) for the node on the network.  The
	 * node will be assigned an initial bank node set.
	 * 
	 * @return The newly added node
	 */
	Node addNode(); 
	
	/**
	 * Removes the node from the network.  This method will attempt to permanently
	 * remove all information associated with the node from the network.  
	 */
	void removeNode();

	/**
	 * Connect a node to the network.  The node must have already been added 
	 * with addNode and must provide the identity that it was given when added.  
	 * After this method the node should be ready to interact with other nodes
	 * on the network.
	 */
	void connect(Node node);
	
	/**
	 * Disconnect the node from the network.  After this method the node will not
	 * be available to interact with other nodes on the network.  The node's 
	 * identity will be preserved.
	 * @param node
	 */
	void disconnect();

	/**
	 * Adds the node as provider for the data with the given id 
	 */
	void registerAsProviderForData(UUID dataId);

	/**
	 * Removes the node as provider for the data with the given id 
	 */
	void unregisterFromProvidersForData(DataProviderNode dataProvider, UUID dataId);
	
	/**
	 * Returns the data provider with the given id
	 */
	DataProviderNode getDataProvider(UUID providerId);

	/**
	 * Returns a group of data providers for the data with the given id
	 */
	NodeGroup<DataProviderNode> getDataProvidersForData(UUID dataId);
		
	/**
	 * Returns a group of bank nodes for the node with the given id
	 */
	NodeGroup<BankNode> getBankNodesForNode(UUID nodeId);
	
	/**
	 * Returns a group of broker nodes for the data with the given id
	 */
	NodeGroup<BrokerNode> getBrokerNodesForData(UUID dataId);
	
	/**
	 * Returns a group of broker nodes with the given ids
	 */
	NodeGroup<BrokerNode> getBrokerNodes(Set<UUID> brokerNodeIds);

	/**
	 * Returns the number of brokers needed to authorize a transaction for
	 * the given data id
	 */
	int getBrokerThresholdForData(UUID dataId);

	/**
	 * Returns the number of bankers needed to authorize transactions for the 
	 * given node
	 */
	int getBankNodeThreshold(UUID nodeId);

}
