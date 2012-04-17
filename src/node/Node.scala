package node

import java.util.UUID

abstract class Node[T <: Node[T]](val id: UUID = UUID.randomUUID()) {
  val connections: scala.collection.mutable.Set[Node]
  val files = scala.collection.mutable.Set[Block]()

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Node] && that.hashCode == this.hashCode
  }

  override def hashCode = id.hashCode

  def link(that: T)
  def unlink(that: T)

  def share(block: Block) {
    files += block
  }

  def unshare(block: Block) {
    files -= block
  }
}

