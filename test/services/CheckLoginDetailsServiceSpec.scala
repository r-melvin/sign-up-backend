package services

import play.api.libs.json.{JsObject, Json}
import play.api.mvc.Request
import play.api.test.FakeRequest
import repositories.mocks.MockAccountsRepository
import services.CheckLoginDetailsService.{DatabaseFailure, LoginDetailsFound, LoginDetailsNotFound}
import utils.TestConstants.testLoginDetails
import utils.UnitSpec

import scala.concurrent.ExecutionContext.Implicits.global

class CheckLoginDetailsServiceSpec extends UnitSpec with MockAccountsRepository {

  object TestCheckLoginDetailsService extends CheckLoginDetailsService(mockAccountsRepository)

  implicit val request: Request[_] = FakeRequest()

  "CheckLoginDetailsService" should {
    val testJson: JsObject = Json.toJsObject(testLoginDetails)

    "return LoginDetailsMatch" when {
      "the provided details match those stored in mongo" in {
        mockFindByFieldSuccess(testJson)

        val result = TestCheckLoginDetailsService.checkLoginDetails(testLoginDetails)

        await(result) mustBe Right(LoginDetailsFound)
      }
    }

    "return LoginDetailsNotFound" when {
      "the provided details could not be found in mongo" in {
        mockFindByFieldNotFoundFailure(testJson)

        val result = TestCheckLoginDetailsService.checkLoginDetails(testLoginDetails)

        await(result) mustBe Left(LoginDetailsNotFound)
      }
    }

    "return DatabaseFailure" when {
      "the repository fails" in {
        mockFindByFieldFailure(testJson)

        val result = TestCheckLoginDetailsService.checkLoginDetails(testLoginDetails)

        await(result) mustBe Left(DatabaseFailure)
      }
    }
  }

}
