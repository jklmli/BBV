package routing.direct

import node.Node

class CliqueNode extends Node {


  def link(other: Node) {
    connections += other
  }

  def unlink(other: Node) {
    connections -= other
  }
}

