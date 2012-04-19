package main.scala.network.direct

import main.scala.node.Node

class DirectNode extends Node[DirectNode] {
  override val connections = scala.collection.mutable.Set[DirectNode]()

  override def link(that: DirectNode) {
    super.link(that)
    connections += that
  }

  override def unlink(that: DirectNode) {
    super.link(that)
    connections -= that
  }
}

