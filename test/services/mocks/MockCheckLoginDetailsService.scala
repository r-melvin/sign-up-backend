package services.mocks

import models.LoginDetailsModel
import org.mockito.ArgumentMatchers
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Suite}
import play.api.mvc.Request
import services.CheckLoginDetailsService
import services.CheckLoginDetailsService._

import scala.concurrent.Future

trait MockCheckLoginDetailsService extends MockitoSugar with BeforeAndAfterEach {
  self: Suite =>

  val mockCheckLoginDetailsService: CheckLoginDetailsService = mock[CheckLoginDetailsService]

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockCheckLoginDetailsService)
  }

  private def mockCheckLoginDetailsService(loginDetails: LoginDetailsModel)(response: Future[CheckLoginDetailsResponse]): Unit = {
    when(mockCheckLoginDetailsService.checkLoginDetails(
      ArgumentMatchers.eq(loginDetails)
    )(ArgumentMatchers.any[Request[_]])) thenReturn response
  }

  def mockCheckLoginDetailsSuccess(loginDetails: LoginDetailsModel): Unit = {
    mockCheckLoginDetailsService(loginDetails)(Future.successful(Right(LoginDetailsFound)))
  }

  def mockCheckLoginDetailsFailure(loginDetails: LoginDetailsModel): Unit = {
    mockCheckLoginDetailsService(loginDetails)(Future.successful(Left(LoginDetailsNotFound)))
  }

  def mockCheckLoginDetailsDatabaseFailure(loginDetails: LoginDetailsModel): Unit = {
    mockCheckLoginDetailsService(loginDetails)(Future.successful(Left(DatabaseFailure)))
  }

}
