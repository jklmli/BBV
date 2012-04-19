package routing

import main.scala.data.Data
import main.scala.node.Node

abstract class Network {
  val nodes = scala.collection.mutable.Set[Node]()
  def size: nodes.size

  def join(newbie: Node) = {
    assert !(nodes contains newbie)
    nodes += newbie
  }
  def leave(member: Node) = {
    assert (nodes contains member)
    nodes -= member

    member.connections
      .foreach(_ disconnect this)
  }

  def transfer(sender: Node, receiver: Node, block: Data)

  def connect(node1: Node, node2: Node) = {
    node1.link(node2)
    node2.link(node1)
  }
  def disconnect(node1: Node, node2: Node) = {
    node1.unlink(node2)
    node2.unlink(node1)
  }
}