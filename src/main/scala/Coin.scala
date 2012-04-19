package main.scala

import java.util.UUID

class Coin {
  val id: UUID = _
  var owner: UUID = _

  def transferTo(ownerId: UUID) {
    this.owner = ownerId
  }

  def isValid: Boolean = {
    true
  }
}