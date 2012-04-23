package main.scala.node

import main.scala.data.Data
import main.scala.util.MixableActor

trait User extends MixableActor {
  val files = scala.collection.mutable.Set[Data]()

  receive ({
    case ("die") =>
      exit()
    case ("share", file: Data) =>
      store(file)
    case ("unshare", file: Data) =>
      unstore(file)
  }: PartialFunction[Any, Unit])

  protected def store(file: Data) {
    assert(!(files contains file))
    files += file
  }

  protected def unstore(file: Data) {
    assert(files contains file)
    files -= file
  }
}
