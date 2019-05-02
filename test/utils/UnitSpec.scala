package utils

import org.scalatestplus.play.PlaySpec

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class UnitSpec extends PlaySpec {

  def await[A](future: Future[A]): A = Await.result(future, 5 seconds)

}
