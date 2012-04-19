package main.scala.node

import java.util.UUID

abstract class Node[T <: Node[T]](val id: UUID = UUID.randomUUID()) {
  def connections: scala.collection.mutable.Set[T]

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Node[T]] && that.hashCode == this.hashCode
  }

  override def hashCode = id.hashCode

  def link(that: T)

  def unlink(that: T)
}

