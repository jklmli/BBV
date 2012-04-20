package main.scala.network

import main.scala.data.Data
import main.scala.node.{Node, Producer, Consumer}

abstract class Network[T <: Node[T]] {
  val nodes = scala.collection.mutable.Set[T]()
  def size = nodes.size

  def join(newbie: T) {
    assert (!(nodes contains newbie))
    nodes += newbie
  }
  def leave(member: T) {
    assert (nodes contains member)
    nodes -= member

    member.connections foreach(disconnect(_, member))
  }

  def route(sender: T with Producer, receiver: T with Consumer): Traversable[T]

  def transfer(sender: T with Producer, receiver: T with Consumer, file: Data) {
    val path = route(sender, receiver)

    if (!path.isEmpty) {
      sender send file

      val nextHop = route(sender, receiver).tail.head

      nextHop.asInstanceOf[T with Consumer] receive file

      if (nextHop != receiver) {
        transfer(nextHop.asInstanceOf[T with Producer], receiver, file)
      }
    }
  }

  def connect(node1: T, node2: T) {
    node1 link node2
    node2 link node1
  }
  def disconnect(node1: T, node2: T) {
    node1 unlink node2
    node2 unlink node1
  }
}