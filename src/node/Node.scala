package node

import java.util.UUID
                                                   s
abstract class Node(val id: UUID = UUID.randomUUID()) {
  val connections: scala.collection.mutable.Set[Node]
  val files = scala.collection.mutable.Set[Block]()

  def link(other: Node)
  def unlink(other: Node)

  def share(block: Block) {
    files += block
  }

  def unshare(block: Block) {
    files -= block
  }
}

