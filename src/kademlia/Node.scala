import java.util.UUID
import util.Random

import scala.collection.mutable.Set

object Node {
  // UUID hashcodes are Ints, which are 32 bits
  private final val buckets = 32
  private final val bucketDepth = 4

  private final val threads = 3
}

class Node(introducer: Node) {

  val id = UUID.randomUUID()

  // Initially associate 1-10 'files' (represented by hashes) with this Node.
  val files = Set[UUID]()
  files ++=
    (1 until (new Random()).nextInt(10) toList)
      .map(_ => UUID.randomUUID())

  private val buckets: IndexedSeq[Set[Node]] = IndexedSeq.fill(Node.buckets)(Set())

  this connect introducer

  def ping(node: Node): Boolean = closestNodes(node.id).exists(_ == node)

  // Finds the _bucketDepth_ closest Nodes
  private def closestNodes(key: UUID): scala.collection.immutable.Set[Node] = {
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

  def findNode(key: UUID): Node = {
    new Node()
  }

  def findFile(that: Node, key: UUID): Boolean = {
    new Boolean()
  }

  def send(that: Node, key: UUID) {
    that.receive(this, key)
  }

  def receive(that: Node, key: UUID) {
    assert(!this.files(key))

    this.files += key
  }

  def +(that: Node) {
    this.connect(that)
    that.connect(this)

    this
  }

  def -(that: Node) {
    this.disconnect(that)
    that.disconnect(this)

    this
  }

  def die() {
    this.buckets
      .flatten(set => set)
      // Notify all peers of exit
      .foreach(_ disconnect this)
  }

  private def connect(that: Node) {
    bucketWith(that) += that
  }

  private def disconnect(that: Node) {
    bucketWith(that) -= that
  }

  // Returns the bucket containing a node that exists.
  private def bucketWith(that: Node): Set[Node] = {
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