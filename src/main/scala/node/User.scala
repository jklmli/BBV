package main.scala.node

import main.scala.data.Data

trait User {
  val files = scala.collection.mutable.Set[Data]()

  def store(file: Data) {
    assert(!(files contains file))
    files += file
  }

  def unstore(file: Data) {
    assert(files contains file)
    files -= file
  }
}
