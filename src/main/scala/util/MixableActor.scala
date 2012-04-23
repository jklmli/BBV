package main.scala.util

trait MixableActor {
  self: MultiActor =>

  protected def receive: PartialFunction[Any, Unit]

  acts = receive :: acts
}