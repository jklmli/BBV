package main.scala.simulation

import main.scala.data.Data
import main.scala.network.{Network, NaiveNode}
import main.scala.node.{Banker, Consumer, Producer}

object Square extends App {
  val n = new Network[NaiveNode]

  // Leechers
  val a = new NaiveNode with Consumer
  val b = new NaiveNode with Consumer with Banker
  // Seeders
  val c = new NaiveNode with Producer
  val d = new NaiveNode with Producer

  n.join(a)
  n.join(b)
  n.join(c)
  n.join(d)

  n.connect(a, b)
  n.connect(b, c)
  n.connect(c, d)
  n.connect(d, a)

  val file1 = new Data("file1")

  c.start()
  b.start()

  c ! ("share", file1)
  assert(b.files.size == 0)
  Thread.sleep(100)
  n.transfer(c, b, file1)
  Thread.sleep(100)
  assert(b.files.size == 1)

  c ! ("die")
  b ! ("die")

  // Compile-time error!
  // n.transfer(a, b, file1)
}



