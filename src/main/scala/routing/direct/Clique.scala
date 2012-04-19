package routing.direct

import main.scala.data.Data
import routing.Network

class Clique extends Network {
  override def transfer(sender: CliqueNode, receiver: CliqueNode, file: Data) {
    receiver.store(file)
  }
}