package routing.direct;

import node.Node;

import java.util.UUID;

import java.util.Set;
import java.util.HashSet;

public class DirectNode implements Node {
  private UUID id;
  private Set<Node> connections = new HashSet<Node>();

  public UUID id(){
    return id;
  }

  public void link(Node other) {
    connections.add(other);
  }

  public void unlink(Node other) {
    connections.remove(other);
  }

  public void share(Block block);
}
