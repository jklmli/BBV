package main.scala.node

import main.scala.data.Data

trait Consumer extends User {
  receive({
    case ("receive", file: Data) =>
      store(file)
  }: PartialFunction[Any, Unit])
}
