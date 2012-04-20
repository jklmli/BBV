package main.scala.node

import main.scala.data.Account

trait Banker {
  val bank = scala.collection.mutable.Map[Node, Account]()

  def account(node: Node): Account = {
    if (!(bank.keys contains node)) {
      bank(node) = new Account
    }
    bank(node)
  }

  def openAccount(node: Node) {
    assert(!(bank.keys contains node))
    bank(node) = new Account
  }

  def closeAccount(node: Node) {
    assert(bank.keys contains node)
    bank -= node
  }
}