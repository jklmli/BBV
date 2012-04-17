package routing;

import node.Node;

public interface Network {
  public void transfer(Node sender, Node receiver, Block block);
  public void connect(Node node1, Node node2);
  public void disconnect(Node node1, Node node2);
}
