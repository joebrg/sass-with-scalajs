import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

@JSExport("RtlMain")
object Main extends js.JSApp {
  def main(): Unit = {
    println(s"hello2 ${A.hello("2")}")
  }
}
