import collection.immutable.Set
import util.Random

class Network {
  var nodes = Set[Node]()
  
  private def select[A](iter: Iterable[A], index: Int): A =
    iter.slice(index, index).head

  private def random[A](iter: Iterable[A]): A = {
    select(iter, new Random().nextInt(iter.size))
  }

  // Default to a random node for introductions.
  def join(introducer: Node = random(nodes)): Node = {
    val member = new Node(introducer)
    this.nodes += member
    member
  }

  def leave(node: Node): Set[Node] = {
    node.die()

    this.nodes
  }

  def transfer(key: Int, sender: Node, receiver: Node) {
    sender.send(receiver, key)
  }

  def lookup(id: Int): Node = {
    val matches = nodes.filter(node => node.id == id)
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
}
