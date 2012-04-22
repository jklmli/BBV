package main.scala.node

import actors.Actor
import main.scala.data.Account

trait Banker extends Actor {
  private val bank = scala.collection.mutable.Map[Node[_], Account]()

  def act(){
    loop{
      react{
        case("open", node: Node) =>
          openAccount(node)
        case("close", node: Node) =>
          closeAccount(node)
      }
    }
  }

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