package main.scala.node

import main.scala.data.Data

trait Consumer extends User {
  override def receive = {
    super.receive orElse {
      case ("receive", file: Data) =>
        store(file)
    }
  }
}
