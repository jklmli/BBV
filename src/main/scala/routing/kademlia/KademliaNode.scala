import java.util.UUID

import main.scala.node.Node
import main.scala.data.Data

object KademliaNode {
  // UUID hashcodes are Ints, which are 32 bits
  private final val buckets = 32
  private final val bucketDepth = 4

  private final val threads = 3
}

class KademliaNode extends Node[KademliaNode] {

  private val buckets = Seq.fill(KademliaNode.buckets)(scala.collection.mutable.Set[KademliaNode]())
  override def connections = this.buckets.flatten(set => set).asInstanceOf[scala.collection.mutable.Set[KademliaNode]]

  override def link(that: KademliaNode) {
    bucketWith(that) += that
  }

  override def unlink(that: KademliaNode) {
    bucketWith(that) -= that
  }

  def ping(node: KademliaNode): Boolean = closestNodes(node.id).exists(_ == node)


  def findNode(key: UUID): KademliaNode = {
    new KademliaNode()
  }

  def findFile(key: UUID): Boolean = {
    true
  }

  def send(that: KademliaNode, file: Data) {
    // TODO: unimplemented
  }

  def receive(that: KademliaNode, file: Data) {
    // TODO: unimplemented
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