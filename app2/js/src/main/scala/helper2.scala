import common.B

object A {
  val b2 = B
  def hello(a: String): String = B.hello(a)
}
