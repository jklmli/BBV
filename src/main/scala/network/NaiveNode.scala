package main.scala.network

import main.scala.node.{Consumer, Node}

class NaiveNode extends Node[NaiveNode] {
  private val neighbors = scala.collection.mutable.Set[NaiveNode]()

  override def connections = neighbors.toSet

  override def link(that: NaiveNode) {
    super.link(that)
    neighbors += that
  }

  override def unlink(that: NaiveNode) {
    super.unlink(that)
    neighbors -= that
  }

  override def pathTo(that: NaiveNode with Consumer):
    Traversable[NaiveNode with Consumer] = {
    if (connections contains that) List(that) else None
  }
}

