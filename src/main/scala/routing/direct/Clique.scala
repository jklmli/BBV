package main.scala.routing.direct

import main.scala.data.Data
import main.scala.routing.Network
import main.scala.node.Consumer

class Clique extends Network[CliqueNode] {
  override def transfer(sender: CliqueNode with Consumer,
                        receiver: CliqueNode with Consumer, file: Data) {
    receiver store file
  }
}