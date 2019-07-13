package services

import models.LoginDetailsModel
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.Request
import play.api.test.FakeRequest
import play.api.test.Helpers._
import repositories.mocks.MockAccountsRepository
import services.CheckLoginDetailsService._
import utils.TestConstants._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CheckLoginDetailsServiceSpec extends PlaySpec with MockAccountsRepository {

  object TestCheckLoginDetailsService extends CheckLoginDetailsService(mockAccountsRepository)

  implicit val request: Request[_] = FakeRequest()

  "CheckLoginDetailsService" should {
    val testJson: JsObject = Json.toJsObject(testLoginDetails)
    val email = testLoginDetails.email

    "return LoginDetailsMatch" when {
      "the provided details match those stored in mongo" in {
        mockFindById(email)(Future.successful(Some(testJson)))

        val result = TestCheckLoginDetailsService.checkLoginDetails(testLoginDetails)

        await(result) mustBe Right(LoginDetailsMatch)
      }
    }

    "return LoginDetailsDoNotMatch" when {
      "the provided details could not be found in mongo" in {
        val testJson = Json.toJsObject(LoginDetailsModel("", ""))

        mockFindById(email)(Future.successful(Some(testJson)))

        val result = TestCheckLoginDetailsService.checkLoginDetails(testLoginDetails)

        await(result) mustBe Left(LoginDetailsDoNotMatch)
      }
    }

    "return LoginDetailsNotFound" when {
      "the provided details could not be found in mongo" in {
        mockFindById(email)(Future.successful(None))

        val result = TestCheckLoginDetailsService.checkLoginDetails(testLoginDetails)

        await(result) mustBe Left(LoginDetailsNotFound)
      }
    }

    "return DatabaseFailure" when {
      "the repository fails" in {
        mockFindById(email)(Future.failed(new Exception))

        val result = TestCheckLoginDetailsService.checkLoginDetails(testLoginDetails)

        await(result) mustBe Left(DatabaseFailure)
      }
    }
  }

}
