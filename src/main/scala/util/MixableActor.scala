package main.scala.util

trait MixableActor {
  self: MultiActor =>

  protected def mixableAct: PartialFunction[Any, Unit]

  acts = mixableAct :: acts
}