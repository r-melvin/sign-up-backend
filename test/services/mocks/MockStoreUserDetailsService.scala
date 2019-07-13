package services.mocks

import models.UserDetailsModel
import org.scalamock.scalatest.MockFactory
import play.api.mvc.Request
import services.StoreUserDetailsService
import services.StoreUserDetailsService._

import scala.concurrent.Future

trait MockStoreUserDetailsService extends MockFactory {

  val mockStoreUserDetailsService: StoreUserDetailsService = mock[StoreUserDetailsService]

  def mockStoreUserDetails(id: String, userDetails: UserDetailsModel)(response: Future[StoreUserDetailsResponse]): Unit = {
    (mockStoreUserDetailsService.storeUserDetails(_: String, _: UserDetailsModel)(_: Request[_]))
      .expects(id, userDetails, *)
      .returning(response)
  }

}
