package repositories.mocks

import org.mockito.ArgumentMatchers
import org.mockito.Mockito._
import org.openqa.selenium.NotFoundException
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Suite}
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import repositories.AccountsRepository

import scala.concurrent.Future

trait MockAccountsRepository extends MockitoSugar with BeforeAndAfterEach {
  self: Suite =>

  val mockAccountsRepository: AccountsRepository = mock[AccountsRepository]

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockAccountsRepository)
  }

  def mockInsertSuccess(json: JsObject): Unit = {
    when(mockAccountsRepository.insert(ArgumentMatchers.eq(json))) thenReturn Future.successful(mock[WriteResult])
  }

  def mockInsertFailure(json: JsObject): Unit = {
    when(mockAccountsRepository.insert(ArgumentMatchers.eq(json))) thenReturn Future.failed(new Exception)
  }

}