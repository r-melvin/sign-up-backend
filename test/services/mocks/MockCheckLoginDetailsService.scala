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

  private def mockCheckLoginDetailsService(id: String, loginDetails: LoginDetailsModel)(response: Future[CheckLoginDetailsResponse]): Unit = {
    when(mockCheckLoginDetailsService.checkLoginDetails(
      ArgumentMatchers.eq(id),
      ArgumentMatchers.eq(loginDetails)
    )(ArgumentMatchers.any[Request[_]])) thenReturn response
  }

  def mockCheckLoginDetailsSuccess(id: String, loginDetails: LoginDetailsModel): Unit = {
    mockCheckLoginDetailsService(id, loginDetails)(Future.successful(Right(LoginDetailsMatch)))
  }

  def mockCheckLoginDetailsDoNotMatchFailure(id: String, loginDetails: LoginDetailsModel): Unit = {
    mockCheckLoginDetailsService(id, loginDetails)(Future.successful(Left(LoginDetailsDoNotMatch)))
  }

  def mockCheckLoginDetailsNotFoundFailure(id: String, loginDetails: LoginDetailsModel): Unit = {
    mockCheckLoginDetailsService(id, loginDetails)(Future.successful(Left(LoginDetailsNotFound)))
  }

  def mockCheckLoginDetailsDatabaseFailure(id: String, loginDetails: LoginDetailsModel): Unit = {
    mockCheckLoginDetailsService(id, loginDetails)(Future.successful(Left(DatabaseFailure)))
  }

}
