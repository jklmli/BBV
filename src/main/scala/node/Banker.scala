package main.scala.node

import java.util.UUID

import main.scala.Coin

trait Banker {
  val bank = scala.collection.mutable.Set[Coin]()

  def deposit(coin: Coin) {
    assert(!(bank contains coin))
    bank += coin
  }

  def withdraw(coin: Coin) {
    assert(bank contains coin)
    bank -= coin
  }

  def getCurrencyUnits(nodeId: UUID, ids: Traversable[UUID]): Traversable[Coin]
}