package main.scala.network.direct

import main.scala.network.Network
import main.scala.node.{Producer, Consumer}

class Direct extends Network[DirectNode] {
  def hops(from: DirectNode, to: DirectNode):
    Traversable[DirectNode with Producer with Consumer] = {
    if (from.connections contains to) List() else None
  }
}