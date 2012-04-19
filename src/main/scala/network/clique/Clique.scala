package main.scala.network.clique

import main.scala.data.Data
import main.scala.network.Network
import main.scala.node.{Producer, Consumer}

class Clique extends Network[CliqueNode] {
  override def transfer(sender: CliqueNode with Producer,
                        receiver: CliqueNode with Consumer, file: Data) {
    receiver store file
  }
}