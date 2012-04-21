package main.scala.network

import main.scala.node.{Producer, Consumer, Node}


class DirectNode extends Node[DirectNode] {
  private val links = scala.collection.mutable.Set[DirectNode]()

  override def connections = links.toSet

  override def link(that: DirectNode) {
    super.link(that)
    links += that
  }

  override def unlink(that: DirectNode) {
    super.unlink(that)
    links -= that
  }

  override def pathTo(that: DirectNode with Consumer):
    Traversable[DirectNode with Consumer] = {
    if (connections contains that) List(that) else None
  }
}

