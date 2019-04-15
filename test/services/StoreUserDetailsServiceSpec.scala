package services

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.Request
import play.api.test.FakeRequest
import repositories.mocks.MockAccountsRepository
import services.StoreUserDetailsService._
import utils.TestConstants.testUserDetails

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class StoreUserDetailsServiceSpec extends PlaySpec with MockAccountsRepository {

  object TestStoreUserDetailsService extends StoreUserDetailsService(mockAccountsRepository)

  def await[A](future: Future[A]): A = Await.result(future, 5 seconds)

  implicit val request: Request[_] = FakeRequest()

  "StoreUserDetailsService" should {
    val testJson: JsObject = Json.toJsObject(testUserDetails)

    "return UserDetailsStored" when {
      "the repository has successful stored the details in mongo" in {
        mockInsertSuccess(testJson)

        val result = TestStoreUserDetailsService.storeUserDetails(testUserDetails)

        await(result) mustBe Right(UserDetailsStored)
      }
    }
    "return UserDetailsNotStored" should {
      "when the repository has failed" in {
        mockInsertFailure(testJson)

        val result = TestStoreUserDetailsService.storeUserDetails(testUserDetails)

        await(result) mustBe Left(StoreUserDetailsFailure)
      }
    }
  }

}
