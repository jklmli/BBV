package main.scala.data

class Data(val payload: String) {
  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Data] && that.hashCode == this.hashCode
  }

  override def hashCode: Int = payload.hashCode
}

