package main.scala.node

import main.scala.data.Account
import main.scala.util.MixableActor

trait Banker extends MixableActor {
  private val bank = scala.collection.mutable.Map[Node[_], Account]()

  receive ({
    case ("open", node: Node[_]) =>
      openAccount(node)
    case ("close", node: Node[_]) =>
      closeAccount(node)
  }: PartialFunction[Any, Unit])

  def account(node: Node[_]): Account = {
    bank(node)
  }

  private def openAccount(node: Node[_]) {
    assert(!(bank.keys.iterator contains node))
    bank(node) = new Account
  }

  private def closeAccount(node: Node[_]) {
    assert(bank.keys.iterator contains node)
    bank -= node
  }
}