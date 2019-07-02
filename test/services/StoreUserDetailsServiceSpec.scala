package services

import play.api.libs.json.{JsObject, Json}
import play.api.mvc.Request
import play.api.test.FakeRequest
import repositories.mocks.MockAccountsRepository
import services.StoreUserDetailsService._
import utils.TestConstants._
import utils.UnitSpec

import scala.concurrent.ExecutionContext.Implicits.global

class StoreUserDetailsServiceSpec extends UnitSpec with MockAccountsRepository {

  object TestStoreUserDetailsService extends StoreUserDetailsService(mockAccountsRepository)

  implicit val request: Request[_] = FakeRequest()

  "StoreUserDetailsService" should {
    val testJson: JsObject = Json.toJsObject(testUserDetails)

    "return UserDetailsStored" when {
      "the repository has successful stored the details in mongo" in {
        mockInsertSuccess(testRequestId, testJson)

        val result = TestStoreUserDetailsService.storeUserDetails(testRequestId, testUserDetails)

        await(result) mustBe Right(UserDetailsStored)
      }
    }

    "return DatabaseFailure" when {
      "the repository has failed" in {
        mockInsertFailure(testRequestId, testJson)

        val result = TestStoreUserDetailsService.storeUserDetails(testRequestId, testUserDetails)

        await(result) mustBe Left(DatabaseFailure)
      }
    }
  }

}
