package main.scala.node

import main.scala.data.Account

trait Banker {
  val bank = scala.collection.mutable.Map[Node[_], Account]()

  def account(node: Node[_]): Account = {
    if (!(bank.keys.iterator contains node)) {
      bank(node) = new Account
    }
    bank(node)
  }

  def openAccount(node: Node[_]) {
    assert(!(bank.keys.iterator contains node))
    bank(node) = new Account
  }

  def closeAccount(node: Node[_]) {
    assert(bank.keys.iterator contains node)
    bank -= node

  }
}