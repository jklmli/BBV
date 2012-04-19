package main.scala.data

class Data(val bytes: Array[Byte]) {
  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Data] && that.hashCode == this.hashCode
  }

  override def hashCode: Int = bytes.hashCode
}

