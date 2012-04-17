package routing.direct

import node.Node
import routing.Network

class DirectNetwork extends Network {
  override def transfer(sender: Node, receiver: Node, block: Block) {
    receiver.share(block)
  }

  override def connect(node1: Node, node2: Node) {
    node1.link(node2)
    node2.link(node1)
  }

  override def disconnect(node1: Node, node2: Node) {
    node1.unlink(node2)
    node2.unlink(node1)
  }
}