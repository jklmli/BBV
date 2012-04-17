package routing

import node.Node

abstract class Network {
  def transfer(sender: Node, receiver: Node, block: Block)
  def connect(node1: Node, node2: Node)
  def disconnect(node1: Node, node2: Node)
}