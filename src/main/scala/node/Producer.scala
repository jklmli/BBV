package main.scala.node

import main.scala.data.Data

trait Producer extends User {
  def send(block: Data) {
    // TODO: notify Logger?
  }
}
