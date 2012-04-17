package routing

import node.Node

abstract class Network {
  val nodes = scala.collection.mutable.Set[Node]()

  def join(newbie: Node) = {
    assert !(nodes contains newbie)
    nodes += newbie
  }
  def leave(member: Node) = {
    assert (nodes contains member)
    nodes -= member
  }

  def transfer(sender: Node, receiver: Node, block: Block)
  def connect(node1: Node, node2: Node)
  def disconnect(node1: Node, node2: Node)
}