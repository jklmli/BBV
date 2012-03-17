package edu.uiuc.cs598.project.bbv.dht;

import java.util.List;
import java.util.UUID;

import edu.uiuc.cs598.project.bbv.node.Node;

public interface DHTInterface {

	public void registerNodeResource(Node node, UUID resourceId);

	public List<Node> getNodesWithResource(UUID resourceId);

	public void unregisterNodeResource(Node node, UUID resourceId);

}
