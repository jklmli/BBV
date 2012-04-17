package routing.direct

import data.Data
import routing.Network

class Clique extends Network {
  override def transfer(sender: CliqueNode, receiver: CliqueNode, data: Data) {
    receiver.store(block)
  }
}