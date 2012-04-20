package main.scala.network.kademlia

import java.util.UUID
import util.Random

import main.scala.network.Network
import main.scala.node.{Producer, Consumer}


class Kademlia extends Network[KademliaNode] {
  def hops(from: KademliaNode, to: KademliaNode):
    Traversable[KademliaNode with Producer with Consumer] = {
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
