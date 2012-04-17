package routing.direct

import node.Node
import java.util.UUID

class DirectNode extends Node {
  val connections = scala.collection.mutable.Set[Node]()

  def link(other: Node) {
    connections.add(other)
  }

  def unlink(other: Node) {
    connections.remove(other)
  }

  def share(block: Block) {

  }
}

