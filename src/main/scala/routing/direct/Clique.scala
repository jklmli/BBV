package main.scala.routing.direct

import main.scala.data.Data
import main.scala.routing.Network

class Clique extends Network[CliqueNode] {
  override def transfer(sender: CliqueNode, receiver: CliqueNode, file: Data) {
    receiver store file
  }
}