package routing.direct;

import node.Node;

import routing.Network;

public class DirectNetwork implements Network {
  public void transfer(Node sender, Node receiver, Block block) {
    receiver.share(block);
  }
  public void connect(Node node1, Node node2){
    node1.link(node2);
    node2.link(node1);
  }
  public void disconnect(Node node1, Node node2){
    node1.unlink(node2);
    node2.unlink(node1);
  }
}
