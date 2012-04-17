package node

import data.Data

import java.util.UUID

abstract class Node[T <: Node[T]](val id: UUID = UUID.randomUUID()) {
  val connections: scala.collection.mutable.Set[Node]
  val files = scala.collection.mutable.Set[Data]()

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Node] && that.hashCode == this.hashCode
  }

  override def hashCode = id.hashCode

  def link(that: T)
  def unlink(that: T)

  def store(block: Data) {
    assert !(files contains block)
    files += block
  }

  def unstore(block: Data) {
    assert (files contains block)
    files -= block
  }
}

