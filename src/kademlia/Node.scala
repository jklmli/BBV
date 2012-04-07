import java.util.UUID

import util.Random

object Node {
  // UUID hashcodes are Ints, which are 32 bits
  private final val buckets = 32
  private final val bucketDepth = 4
}

class Node(introducer: Node) {

  val id = UUID.randomUUID()

  // Initially associate 1-10 'files' (represented by hashes) with this Node.
  var files = Set[UUID]()
  files ++=
    (1 until (new Random()).nextInt(10) toList)
      .map(_ => UUID.randomUUID())

  private var buckets: IndexedSeq[Set[Node]] = IndexedSeq.fill(Node.buckets)(Set())

  this connect introducer

  def ping(node: Node): Boolean = closestNodes(node.id).exists(node)

  // Finds the _bucketDepth_ closest Nodes
  def closestNodes(id: UUID): Set[Node] = {
    // TODO: Inaccurate functionality
    var remaining = Node.bucketDepth

    buckets
      .foldLeft(Set[Node]())((nodes, bucket) =>
      nodes ++ bucket.takeWhile(_ => {
        remaining -= 1
        remaining >= 0
      })
    )
  }

  def findFile(that: Node, key: UUID): Int = {
  }

  def send(that: Node, key: UUID) { that.receive(this, key) }

  def receive(that: Node, key: UUID) {
    assert(!this.files(key))

    this.files += key
  }

  def die() {
    this.buckets
      .flatten(_)
      // Notify all peers of exit
      .foreach(_ disconnect this)
  }

  private def connect(that: Node) { this.buckets(bucketize(that)) += that }

  private def disconnect(that: Node) {
    this.buckets =
      this.buckets
        .map(_ - that)
  }

  // Determine which bucket another node should be placed in.
  private def bucketize(that: Node) = {
    var prefix = ""
    this.id.hashCode.toBinaryString
      .indexWhere(character => {
      prefix += character
      !(that.id.hashCode.toBinaryString startsWith prefix)
    })
  }

  private def distanceTo(that: Node): Int = this.id.hashCode ^ that.id.hashCode
}