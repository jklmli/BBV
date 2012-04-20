package main.scala.node

import main.scala.data.Data

trait Consumer extends User {
  def receive(block: Data) {
    // TODO: notify Logger?
  }
}
