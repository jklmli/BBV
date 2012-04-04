package simulation.dht;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import node.Node;
import dht.DHT;

public class MemoryDHT implements DHT {

	private Map<UUID, List<Node>> resourceNodeMap = new HashMap<UUID, List<Node>>();

	@Override
	public void registerNodeResource(Node node, UUID resourceId) {
		List<Node> nodes = resourceNodeMap.get(resourceId);
		if (nodes == null) {
			nodes = new ArrayList<Node>();
			resourceNodeMap.put(resourceId, nodes);
		}

		nodes.add(node);
	}

	@Override
	public List<Node> getNodesWithResource(UUID resourceId) {
		return resourceNodeMap.get(resourceId);
	}

	@Override
	public void unregisterNodeResource(Node node, UUID resourceId) {
		List<Node> nodes = resourceNodeMap.get(resourceId);

		if (nodes == null || nodes.size() == 0) {
			return;
		}

		nodes.remove(node);
	}
}
