package main.scala.network.direct

import main.scala.data.Data
import main.scala.network.Network
import main.scala.node.{Producer, Consumer}

class Direct extends Network[DirectNode] {
  override def transfer(sender: DirectNode with Producer,
                        receiver: DirectNode with Consumer, file: Data) {
    receiver store file
  }
}