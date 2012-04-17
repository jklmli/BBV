package routing.direct

import node.Node

class CliqueNode extends Node[CliqueNode] {
  override val connections = scala.collection.mutable.Set[Node]()

  override def link(that: CliqueNode) {
    connections += that
  }

  override def unlink(that: CliqueNode) {
    connections -= that
  }
}

