package routing.direct

import routing.Network

class Clique extends Network {
  override def transfer(sender: CliqueNode, receiver: CliqueNode, block: Block) {
    receiver.store(block)
  }
}