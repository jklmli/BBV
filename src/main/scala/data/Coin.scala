package main.scala.data

import java.util.UUID

import main.scala.node.Node

class Coin(val minter: Node[_]) {
  val id = UUID.randomUUID()

  def isValid: Boolean = {
    // TODO: ???
    true
  }

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Coin] && that.hashCode == this.hashCode
  }

  override def hashCode: Int = id.hashCode
}