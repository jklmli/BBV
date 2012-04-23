package main.scala.util

import actors.Actor

class MultiActor extends Actor {
  protected var acts = List[PartialFunction[Any, Unit]]()

  def act() {
    loop {
      react {
        acts.reduce((a, b) => a orElse b)
      }
    }
  }
}
