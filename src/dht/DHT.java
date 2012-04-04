package dht;

import java.util.List;
import java.util.UUID;

import node.Node;

public interface DHT {

	public void registerNodeResource(Node node, UUID resourceId);

	public List<Node> getNodesWithResource(UUID resourceId);

	public void unregisterNodeResource(Node node, UUID resourceId);

}
