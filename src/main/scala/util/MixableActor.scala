package main.scala.util

trait MixableActor extends MultiActor {
  protected def receive: PartialFunction[Any, Unit]

  acts = receive :: acts
}