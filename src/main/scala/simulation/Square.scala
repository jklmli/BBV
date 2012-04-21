package main.scala.simulation

import main.scala.node.{Consumer, Producer}
import main.scala.data.Data
import main.scala.network.{Network, DirectNode}

object Square extends App {
  val n = new Network[DirectNode]

  // Leechers
  val a = new DirectNode with Consumer
  val b = new DirectNode with Consumer
  // Seeders
  val c = new DirectNode with Producer
  val d = new DirectNode with Producer

  n.join(a)
  n.join(b)
  n.join(c)
  n.join(d)

  n.connect(a, b)
  n.connect(b, c)
  n.connect(c, d)
  n.connect(d, a)

  val file1 = new Data("file1")

  c.store(file1)

  assert(b.files.size == 0)
  n.transfer(c, b, file1)
  assert(b.files.size == 1)

  // Compile-time error!
  // n.transfer(a, b, file1)
}



