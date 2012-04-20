package main.scala.data

class Account {
  val balance = scala.collection.mutable.Set[Coin]()

  def deposit(coins: Traversable[Coin]) {
    coins foreach (coin => {
      assert(!(balance contains coin))
      balance += coin
    })
  }

  def withdraw(number: Int) {
    val payment = balance take number
    balance --= payment

    payment
  }
}
