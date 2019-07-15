package repositories.mocks

import org.scalamock.handlers.{CallHandler1, CallHandler2}
import org.scalamock.scalatest.MockFactory
import org.scalatest.Suite
import play.api.libs.json.JsObject
import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import repositories.AccountsRepository

import scala.concurrent.Future

trait MockAccountsRepository extends MockFactory {
  self: Suite =>

  val mockAccountsRepository: AccountsRepository = mock[AccountsRepository]

  def mockInsert(id: String, json: JsObject)(response: Future[WriteResult]): CallHandler2[String, JsObject, Future[WriteResult]] =
    (mockAccountsRepository.insert _)
      .expects(id, json)
      .returning(response)

  def mockUpdate(id: String, json: JsObject)(response: Future[UpdateWriteResult]): CallHandler2[String, JsObject, Future[UpdateWriteResult]] =
    (mockAccountsRepository.update _)
      .expects(id, json)
      .returning(response)

  def mockFindById(id: String)(response: Future[Option[JsObject]]): CallHandler1[String, Future[Option[JsObject]]] =
    (mockAccountsRepository.findById _)
      .expects(id)
      .returning(response)

}