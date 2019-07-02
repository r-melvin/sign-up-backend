package repositories.mocks

import org.mockito.ArgumentMatchers
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Suite}
import play.api.libs.json.JsObject
import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import repositories.AccountsRepository

import scala.concurrent.Future

trait MockAccountsRepository extends MockitoSugar with BeforeAndAfterEach {
  self: Suite =>

  val mockAccountsRepository: AccountsRepository = mock[AccountsRepository]

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockAccountsRepository)
  }

  def mockInsertSuccess(id: String, json: JsObject): Unit = {
    when(mockAccountsRepository.insert(
      ArgumentMatchers.eq(id),
      ArgumentMatchers.eq(json)
    )) thenReturn Future.successful(mock[WriteResult])
  }

  def mockInsertFailure(id: String, json: JsObject): Unit = {
    when(mockAccountsRepository.insert(
      ArgumentMatchers.eq(id),
      ArgumentMatchers.eq(json)
    )) thenReturn Future.failed(new Exception)
  }

  def mockUpdateSuccess(id: String, json: JsObject): Unit = {
    when(mockAccountsRepository.update(
      ArgumentMatchers.eq(id),
      ArgumentMatchers.eq(json)
    )) thenReturn Future.successful(mock[UpdateWriteResult])
  }

  def mockUpdateFailure(id: String, json: JsObject): Unit = {
    when(mockAccountsRepository.update(
      ArgumentMatchers.eq(id),
      ArgumentMatchers.eq(json)
    )) thenReturn Future.failed(new Exception)
  }

  def mockFindById(id: String)(response: Future[Option[JsObject]]): Unit = {
    when(mockAccountsRepository.findById(ArgumentMatchers.eq(id))) thenReturn response
  }

  def mockFindByIdSuccess(id: String, response: JsObject): Unit = {
    mockFindById(id)(Future.successful(Some(response)))
  }

  def mockFindByIdNotFoundFailure(id: String): Unit = {
    mockFindById(id)(Future.failed(new NoSuchElementException))
  }

  def mockFindByIdFailure(id: String): Unit = {
    mockFindById(id)(Future.failed(new Exception))
  }

}