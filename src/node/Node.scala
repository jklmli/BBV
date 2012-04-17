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

  def store(block: Block) {
    assert !(files contains block)
    files += block
  }

  def unstore(block: Block) {
    assert (files contains block)
    files -= block
  }
}

