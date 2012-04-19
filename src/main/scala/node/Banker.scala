package main.scala.node

import node.CurrencyUnit

trait Banker {
  val bank = scala.collection.mutable.Set[CurrencyUnit]()

  def add(currencyUnits: Traversable[CurrencyUnit]) {
    currencyUnits.foreach(currencyUnit => {
      assert(!(bank contains currencyUnit))
      bank += currencyUnit
    })
  }

  def remove(currencyUnits: Traversable[CurrencyUnit]) {
    currencyUnits.foreach(currencyUnit => {
      assert(bank contains currencyUnit)
      bank -= currencyUnit
    })
  }

  def getCurrencyUnits(nodeId: UUID, ids: Traversable[UUID]): Traversable[CurrencyUnit]
}