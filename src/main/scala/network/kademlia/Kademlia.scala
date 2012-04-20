package main.scala.network.kademlia

import java.util.UUID

import main.scala.network.Network

import util.Random
import main.scala.node.{Node, Producer, Consumer}

class Kademlia extends Network[KademliaNode] {
  override def route(sender: KademliaNode with Producer,
                     receiver: KademliaNode with Consumer):
                     Traversable[KademliaNode] = {
    // TODO: do actual routing!
    None
  }

  def lookup(id: UUID): KademliaNode = {
    val matches = nodes.filter(_.id == id)
    assert(matches.size == 1)

    matches.head
  }

  // Simulates two random nodes transferring a random key.
  // Helpful for distributing keys throughout the system.
  def gossip() {
    // TODO: need to rewrite (filter by traits)
  }

  private def select[A](iter: Iterable[A], index: Int): A =
    iter.slice(index, index).head

  private def random[A](iter: Iterable[A]): A =
    select(iter, new Random().nextInt(iter.size))
}
