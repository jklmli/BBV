package main.scala.node

import java.util.UUID

abstract class Node[T <: Node[T]](val id: UUID = UUID.randomUUID()) {
  def connections: Set[T]

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Node[T]] && that.hashCode == this.hashCode
  }

  override def hashCode = id.hashCode

  def link(that: T) {
    assert(!(connections contains that))
  }

  def unlink(that: T) {
    assert(connections contains that)
  }

  def pathTo(that: T with Consumer): Traversable[T with Consumer]
}

