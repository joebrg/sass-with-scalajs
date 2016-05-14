import common.B

object A {
  val b = B
  def hello(a: String): String = B.hello(a)
}
