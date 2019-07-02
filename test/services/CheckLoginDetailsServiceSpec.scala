package services

import play.api.libs.json.{JsObject, Json}
import play.api.mvc.Request
import play.api.test.FakeRequest
import repositories.mocks.MockAccountsRepository
import services.CheckLoginDetailsService._
import utils.TestConstants._
import utils.UnitSpec

import scala.concurrent.ExecutionContext.Implicits.global

class CheckLoginDetailsServiceSpec extends UnitSpec with MockAccountsRepository {

  object TestCheckLoginDetailsService extends CheckLoginDetailsService(mockAccountsRepository)

  implicit val request: Request[_] = FakeRequest()

  "CheckLoginDetailsService" should {
    val testJson: JsObject = Json.toJsObject(testLoginDetails)

    "return LoginDetailsMatch" when {
      "the provided details match those stored in mongo" in {
        mockFindByIdSuccess(testRequestId, testJson)

        val result = TestCheckLoginDetailsService.checkLoginDetails(testRequestId, testLoginDetails)

        await(result) mustBe Right(LoginDetailsMatch)
      }
    }

    "return LoginDetailsDoNotMatch" when {
      "the provided details could not be found in mongo" in {
        val testJson = Json.toJsObject(testInvalidLoginDetails)

        mockFindByIdSuccess(testRequestId, testJson)

        val result = TestCheckLoginDetailsService.checkLoginDetails(testRequestId, testLoginDetails)

        await(result) mustBe Left(LoginDetailsDoNotMatch)
      }
    }

    "return LoginDetailsNotFound" when {
      "the provided details could not be found in mongo" in {
        mockFindByIdNotFoundFailure(testRequestId)

        val result = TestCheckLoginDetailsService.checkLoginDetails(testRequestId, testLoginDetails)

        await(result) mustBe Left(LoginDetailsNotFound)
      }
    }

    "return DatabaseFailure" when {
      "the repository fails" in {
        mockFindByIdFailure(testRequestId)

        val result = TestCheckLoginDetailsService.checkLoginDetails(testRequestId, testLoginDetails)

        await(result) mustBe Left(DatabaseFailure)
      }
    }
  }

}
