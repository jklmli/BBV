import java.util.UUID

import main.scala.data.Data
import routing.Network

import util.Random

class Kademlia extends Network {
  override def transfer(sender: KademliaNode, receiver: KademliaNode, file: Data) {
    sender.send(receiver, file)
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

  private def select[A](iter: Iterable[A], index: Int): A =
    iter.slice(index, index).head

  private def random[A](iter: Iterable[A]): A =
    select(iter, new Random().nextInt(iter.size))
}
