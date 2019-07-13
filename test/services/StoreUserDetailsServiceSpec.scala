package services

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.Request
import play.api.test.FakeRequest
import play.api.test.Helpers._
import reactivemongo.api.commands.WriteResult
import repositories.mocks.MockAccountsRepository
import services.StoreUserDetailsService._
import utils.TestConstants._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class StoreUserDetailsServiceSpec extends PlaySpec with MockAccountsRepository {

  object TestStoreUserDetailsService extends StoreUserDetailsService(mockAccountsRepository)

  implicit val request: Request[_] = FakeRequest()

  "StoreUserDetailsService" should {
    val testJson: JsObject = Json.toJsObject(testUserDetails)

    "return UserDetailsStored" when {
      "the repository has successful stored the details in mongo" in {
        mockInsert(testRequestId, testJson)(Future.successful(mock[WriteResult]))
        val result = TestStoreUserDetailsService.storeUserDetails(testRequestId, testUserDetails)

        await(result) mustBe Right(UserDetailsStored)
      }
    }

    "return DatabaseFailure" when {
      "the repository has failed" in {
        mockInsert(testRequestId, testJson)(Future.failed(new Exception))

        val result = TestStoreUserDetailsService.storeUserDetails(testRequestId, testUserDetails)

        await(result) mustBe Left(DatabaseFailure)
      }
    }
  }

}
