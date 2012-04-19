package main.scala.network.direct

import main.scala.node.Node

class DirectNode extends Node[DirectNode] {
  private val links = scala.collection.mutable.Set[DirectNode]()
  override def connections = links.toSet

  override def link(that: DirectNode) {
    super.link(that)
    links += that
  }

  override def unlink(that: DirectNode) {
    super.link(that)
    links -= that
  }
}

