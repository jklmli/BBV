package main.scala.simulation

import main.scala.data.Data
import main.scala.network.{Network, NaiveNode}
import main.scala.node.{Banker, Consumer, Producer}

object Square extends App {
  val network = new Network[NaiveNode]

  val a = new NaiveNode with Consumer with Banker with Producer
  val b = new NaiveNode with Banker with Consumer with Producer
  val c = new NaiveNode with Producer with Banker with Consumer
  val d = new NaiveNode with Consumer with Producer with Banker

  network.join(a)
  network.join(b)
  network.join(c)
  network.join(d)

  network.connect(a, b)
  network.connect(b, c)
  network.connect(c, d)
  network.connect(d, a)

  val file1 = new Data("file1")

  c.start()
  b.start()

  c ! ("share", file1)
  assert(b.files.size == 0)
  Thread.sleep(100)
  network.transfer(c, b, file1)
  Thread.sleep(100)
  assert(b.files.size == 1)

  network.shutdown()

  // Compile-time error!
  // n.transfer(a, b, file1)
}



