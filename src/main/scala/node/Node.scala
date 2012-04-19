package main.scala.node

import java.util.UUID

import main.scala.data.Data

abstract class Node[T <: Node[T]](val id: UUID = UUID.randomUUID()) {
  def connections: scala.collection.mutable.Set[T]
  val files = scala.collection.mutable.Set[Data]()

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Node[T]] && that.hashCode == this.hashCode
  }

  override def hashCode = id.hashCode

  def link(that: T)

  def unlink(that: T)

  def store(file: Data) {
    assert (!(files contains file))
    files += file
  }

  def unstore(file: Data) {
    assert(files contains file)
    files -= file
  }
}

