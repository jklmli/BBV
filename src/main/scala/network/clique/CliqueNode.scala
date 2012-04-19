package main.scala.network.clique

import main.scala.node.Node

class CliqueNode extends Node[CliqueNode] {
  override val connections = scala.collection.mutable.Set[CliqueNode]()

  override def link(that: CliqueNode) {
    connections += that
  }

  override def unlink(that: CliqueNode) {
    connections -= that
  }
}

