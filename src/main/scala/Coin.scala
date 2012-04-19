package main.scala

import java.util.UUID

class Coin {
  val id: UUID = null
  var owner: UUID = null

  def transferTo(ownerId: UUID) {
    this.owner = ownerId
  }

  def isValid: Boolean = {
    true
  }
}