package services.mocks

import models.UserDetailsModel
import org.mockito.ArgumentMatchers
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Suite}
import play.api.mvc.Request
import services.StoreUserDetailsService
import services.StoreUserDetailsService._

import scala.concurrent.Future

trait MockStoreUserDetailsService extends MockitoSugar with BeforeAndAfterEach {
  self: Suite =>

  val mockStoreUserDetailsService: StoreUserDetailsService = mock[StoreUserDetailsService]

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockStoreUserDetailsService)
  }

  private def mockStoreUserDetailsService(id: String, userDetails: UserDetailsModel)(response: Future[StoreUserDetailsResponse]): Unit = {
    when(mockStoreUserDetailsService.storeUserDetails(
      ArgumentMatchers.eq(id),
      ArgumentMatchers.eq(userDetails)
    )(ArgumentMatchers.any[Request[_]])) thenReturn response
  }

  def mockStoreUserDetailsSuccess(id: String, userDetails: UserDetailsModel): Unit = {
    mockStoreUserDetailsService(id, userDetails)(Future.successful(Right(UserDetailsStored)))
  }

  def mockStoreUserDetailsFailure(id: String, userDetails: UserDetailsModel): Unit = {
    mockStoreUserDetailsService(id, userDetails)(Future.successful(Left(DatabaseFailure)))
  }

}
