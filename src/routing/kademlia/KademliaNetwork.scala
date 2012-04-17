import java.util.UUID

import collection.mutable.Set
import util.Random

class KademliaNetwork {
  private val nodes = Set[KademliaNode]()

  // Default to a random node for introductions.
  def join(introducer: KademliaNode = random(nodes)): KademliaNode = {
    val newbie = new KademliaNode()
    newbie connect introducer

    this.nodes += newbie
    this.nodes
  }

  def leave(member: KademliaNode): Set[KademliaNode] = {
    member.die()

    this.nodes -= member
    this.nodes
  }

  def transfer(id: UUID, sender: KademliaNode, receiver: KademliaNode) {
    sender.send(receiver, id)
  }

  def lookup(id: UUID): KademliaNode = {
    val matches = nodes.filter(_.id == id)
    assert(matches.size == 1)

    matches.head
  }

  // Simulates two random nodes transferring a random key.
  // Helpful for distributing keys throughout the system.
  def gossip() {
    assert(nodes.size >= 2)

    val sender = random(nodes)
    val receiver = random(nodes - sender)
    val file = random(sender.files)

    transfer(file, sender, receiver)
  }

  def size: Int = this.nodes.size

  private def select[A](iter: Iterable[A], index: Int): A =
    iter.slice(index, index).head

  private def random[A](iter: Iterable[A]): A =
    select(iter, new Random().nextInt(iter.size))
}
