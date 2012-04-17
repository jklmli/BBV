import java.util.UUID
import util.Random

object KademliaNode {
  // UUID hashcodes are Ints, which are 32 bits
  private final val buckets = 32
  private final val bucketDepth = 4

  private final val threads = 3
}

class KademliaNode(val id: UUID = UUID.randomUUID()) {

  private val buckets = Seq.fill(KademliaNode.buckets)(scala.collection.mutable.Set[KademliaNode]())
  val files = scala.collection.mutable.Set[UUID]()

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[KademliaNode] && that.hashCode == this.hashCode
  }

  override def hashCode = id.hashCode

  def ping(node: KademliaNode): Boolean = closestNodes(node.id).exists(_ == node)

  def store(key: UUID) {
    assert(!this.files(key))

    this.files += key
  }

  def findNode(key: UUID): KademliaNode = {
    new KademliaNode()
  }

  def findFile(key: UUID): Boolean = {
    true
  }

  def send(that: KademliaNode, key: UUID) {
    that.receive(this, key)
  }

  def receive(that: KademliaNode, key: UUID) {
    store(key)
  }

  def die() {
    this.buckets
      .flatten(set => set)
      // Notify all peers of exit
      .foreach(_ disconnect this)
  }

  def connect(that: KademliaNode): KademliaNode = {
    this.link(that)
    that.link(this)

    this
  }

  def disconnect(that: KademliaNode): KademliaNode = {
    this.unlink(that)
    that.unlink(this)

    this
  }

  // Finds the _bucketDepth_ closest Nodes
  private def closestNodes(key: UUID): Set[KademliaNode] = {
    val closestIndices =
      buckets
        .indices
        .sortBy(x => (x - distanceTo(key)).abs)

    val closestBuckets =
      closestIndices
        .map(buckets(_))

    closestBuckets
      .flatten(set => set)
      .slice(0, KademliaNode.threads)
      .toSet
  }

  private def link(that: KademliaNode) {
    bucketWith(that) += that
  }

  private def unlink(that: KademliaNode) {
    bucketWith(that) -= that
  }

  // Returns the bucket containing a node that exists.
  private def bucketWith(that: KademliaNode): scala.collection.mutable.Set[KademliaNode] = {
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