package repositories.mocks

import org.mockito.ArgumentMatchers
import org.scalatest.{BeforeAndAfterEach, Suite}
import play.api.libs.json.JsObject
import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import repositories.AccountsRepository
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar

import scala.concurrent.Future

trait MockAccountsRepository extends MockitoSugar with BeforeAndAfterEach {
  self: Suite =>

  val mockAccountsRepository: AccountsRepository = mock[AccountsRepository]

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockAccountsRepository)
  }

  def mockInsert(id: String, json: JsObject)(response: Future[WriteResult]): Unit = {
    when(mockAccountsRepository.insert(
      ArgumentMatchers.eq(id),
      ArgumentMatchers.eq(json)
    )) thenReturn response
  }

  def mockUpdate(id: String, json: JsObject)(response: Future[UpdateWriteResult]): Unit = {
    when(mockAccountsRepository.update(
      ArgumentMatchers.eq(id),
      ArgumentMatchers.eq(json)
    )) thenReturn response
  }

  def mockFindById(id: String)(response: Future[Option[JsObject]]): Unit = {
    when(mockAccountsRepository.findById(ArgumentMatchers.eq(id))) thenReturn response
  }

}