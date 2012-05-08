import java.util.UUID
import util.Random

object Node {
  // UUID hashcodes are Ints, which are 32 bits
  private final val buckets = 32
  private final val bucketDepth = 4

  private final val threads = 3
}

class Node(val id: UUID = UUID.randomUUID()) {

  private val buckets = Seq.fill(Node.buckets)(scala.collection.mutable.Set[Node]())
  val files = scala.collection.mutable.Set[UUID]()

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Node] && that.hashCode == this.hashCode
  }

  override def hashCode = id.hashCode

  def ping(node: Node): Boolean = closestNodes(node.id).exists(_ == node)

  def store(key: UUID) {
    assert(!this.files(key))

    this.files += key
  }

  def findNode(key: UUID): Node = {
    new Node()
  }

  def findFile(key: UUID): Boolean = {
    true
  }

  def send(that: Node, key: UUID) {
    that.receive(this, key)
  }

  def receive(that: Node, key: UUID) {
    store(key)
  }

  def die() {
    this.buckets
      .flatten(set => set)
      // Notify all peers of exit
      .foreach(_ disconnect this)
  }

  def connect(that: Node): Node = {
    this.link(that)
    that.link(this)

    this
  }

  def disconnect(that: Node): Node = {
    this.unlink(that)
    that.unlink(this)

    this
  }

  // Finds the _bucketDepth_ closest Nodes
  private def closestNodes(key: UUID): Set[Node] = {
    val closestIndices =
      buckets
        .indices
        .sortBy(x => (x - distanceTo(key)).abs)

    val closestBuckets =
      closestIndices
        .map(buckets(_))

    closestBuckets
      .flatten(set => set)
      .slice(0, Node.threads)
      .toSet
  }

  private def link(that: Node) {
    bucketWith(that) += that
  }

  private def unlink(that: Node) {
    bucketWith(that) -= that
  }

  // Returns the bucket containing a node that exists.
  private def bucketWith(that: Node): scala.collection.mutable.Set[Node] = {
    var prefix = ""
    val index = this.id.hashCode.toBinaryString
      .indexWhere(character => {
      prefix += character
      !(that.id.hashCode.toBinaryString startsWith prefix)
    })

    assert(index != -1)
    buckets(index)
  }

  private def distanceTo(key: UUID): Int = this.id.hashCode ^ key.hashCode
}