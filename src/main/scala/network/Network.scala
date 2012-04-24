package main.scala.network

import main.scala.data.Data
import main.scala.node.{Node, Provider, Consumer}

class Network[T <: Node[T]] {
  val nodes = scala.collection.mutable.Set[T]()

  def size = nodes.size

  def join(newbie: T) {
    assert(!(nodes contains newbie))
    nodes += newbie
  }

  def leave(member: T) {
    assert(nodes contains member)
    nodes -= member

    member.connections foreach (disconnect(_, member))
  }

  def transfer(sender: T with Provider, receiver: T with Consumer, file: Data) {
    assert(sender.files contains file)

    val route = sender.pathTo(receiver)

    if (route != None) {
      sender ! ("send", file)

      // TODO: block until operation completes

      val nextHop = route.head.asInstanceOf[T with Consumer with Provider]
      nextHop ! ("receive", file)

      // TODO: block until operation completes

      if (nextHop != receiver) {
        transfer(nextHop, receiver, file)
      }
    }
    else {
      // TODO: notify Logger?
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

  def shutdown() {
    nodes foreach (_ ! ("die"))
  }
}