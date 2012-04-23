package main.scala.util

import actors.Actor

class MultiActor extends Actor {
  protected var acts = List[PartialFunction[Any, Unit]]()

  final override def act() {
    loop {
      react {
        acts.reduce((a, b) => a orElse b)
      }
    }
  }
}
