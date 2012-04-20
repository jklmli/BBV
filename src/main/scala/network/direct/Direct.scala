package main.scala.network.direct

import main.scala.network.Network
import main.scala.node.{Node, Producer, Consumer}

class Direct extends Network[DirectNode] {
  override def route(sender: DirectNode with Producer,
                     receiver: DirectNode with Consumer) = {
    if (sender.connections contains receiver){
      List(sender, receiver)
    } else None
  }
}