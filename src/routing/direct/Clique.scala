package routing.direct

import node.Node
import routing.Network

class Clique extends Network {
  override def transfer(sender: CliqueNode, receiver: CliqueNode, block: Block) {
    receiver.share(block)
  }
}