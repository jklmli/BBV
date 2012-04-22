package main.scala.node

import main.scala.data.Data
import actors.Actor

trait User extends Actor {
  val files = scala.collection.mutable.Set[Data]()

  def act() {
    loop {
      react {
        case ("store", file: Data) =>
          store(file)
        case ("unstore", file: Data) =>
          unstore(file)
      }
    }
  }

  private def store(file: Data) {
    assert(!(files contains file))
    files += file
  }

  private def unstore(file: Data) {
    assert(files contains file)
    files -= file
  }
}
