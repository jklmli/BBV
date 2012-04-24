package main.scala.node

import main.scala.data.Data

trait Provider extends User {
  def send(block: Data) {
    // TODO: notify Logger?
  }
}
