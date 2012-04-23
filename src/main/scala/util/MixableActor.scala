package main.scala.util

import actors.Actor

class MixableActor extends Actor {
  protected var acts = List[PartialFunction[Any, Unit]]()

  final override def act() {
    loop {
      react {
        acts.reduce((a, b) => a orElse b)
      }
    }
  }

  final def receive(act: PartialFunction[Any, Unit]) {
    acts = act :: acts
  }
}
