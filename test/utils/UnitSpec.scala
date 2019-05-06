package utils

import java.nio.charset.Charset

import akka.stream.Materializer
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Result

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class UnitSpec extends PlaySpec {

  implicit val defaultTimeout: FiniteDuration = 5 seconds

  implicit def extractAwait[A](future: Future[A]): A = await[A](future)

  implicit def liftFuture[A](v: A): Future[A] = Future.successful(v)

  def await[A](future: Future[A])(implicit timeout: Duration): A = Await.result(future, timeout)

  def status(of: Result): Int = of.header.status

  def status(of: Future[Result])(implicit timeout: Duration): Int = status(Await.result(of, timeout))

  def jsonBodyOf(result: Result)(implicit mat: Materializer): JsValue = {
    Json.parse(bodyOf(result))
  }

  def jsonBodyOf(resultF: Future[Result])(implicit mat: Materializer): Future[JsValue] = {
    resultF.map(jsonBodyOf)
  }

  def bodyOf(result: Result)(implicit mat: Materializer): String = {
    await(result.body.consumeData).decodeString(Charset.defaultCharset().name)
  }

  def bodyOf(resultF: Future[Result])(implicit mat: Materializer): Future[String] = {
    resultF.map(bodyOf)
  }

}
