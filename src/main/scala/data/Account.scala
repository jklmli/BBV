package main.scala.data

import actors.Actor

class Account extends Actor {
  private val balance = scala.collection.mutable.Set[Coin]()

  def act() {
    loop {
      react {
        case ("deposit", coins: Traversable[Coin]) =>
          deposit(coins)
        case ("withdraw", number: Int, "for", actor: Actor) =>
          actor ! withdraw(number)
      }
    }
  }

  private def deposit(coins: Traversable[Coin]) {
    coins foreach (coin => {
      assert(!(balance contains coin))
      balance += coin
    })
  }

  private def withdraw(number: Int) {
    val payment = balance take number
    balance --= payment

    payment
  }
}
