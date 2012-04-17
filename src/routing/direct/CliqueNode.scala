package routing.direct

import node.Node

class CliqueNode extends Node {

  def link(that: Node) {
    connections += that
  }

  def unlink(that: Node) {
    connections -= that
  }
}

