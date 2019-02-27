package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class StoreUserDetailsControllerSpec extends PlaySpec with GuiceOneAppPerSuite {

  object TestStoreUserDetailsController extends StoreUserDetailsController(stubControllerComponents())

  val testGetRequest = FakeRequest(GET, "/")

  "StoreUserDetailsController GET" should {
    "return Not Implemented" in {
      val home = TestStoreUserDetailsController.storeUserDetails()(testGetRequest)

      status(home) mustBe NOT_IMPLEMENTED
    }
  }

}
