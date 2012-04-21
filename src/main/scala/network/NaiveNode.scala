package main.scala.network

import main.scala.node.{Consumer, Node}

class NaiveNode extends Node[NaiveNode] {
  private val links = scala.collection.mutable.Set[NaiveNode]()

  override def connections = links.toSet

  override def link(that: NaiveNode) {
    super.link(that)
    links += that
  }

  override def unlink(that: NaiveNode) {
    super.unlink(that)
    links -= that
  }

  override def pathTo(that: NaiveNode with Consumer):
    Traversable[NaiveNode with Consumer] = {
    if (connections contains that) List(that) else None
  }
}

