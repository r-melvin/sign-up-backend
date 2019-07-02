package controllers

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers._
import play.api.test._
import services.mocks.MockCheckLoginDetailsService
import utils.TestConstants._
import utils.UnitSpec

import scala.concurrent.ExecutionContext.Implicits.global

class CheckLoginDetailsControllerSpec extends UnitSpec with MockCheckLoginDetailsService {

  object TestCheckLoginDetailsController extends CheckLoginDetailsController(
    mockCheckLoginDetailsService,
    stubControllerComponents()
  )

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val testPostRequest: FakeRequest[JsValue] = FakeRequest(POST, "/sign-up/check-login-details").withBody(
    Json.toJson(testLoginDetails)
  )

  "CheckLoginDetailsController POST" should {
    "return No Content" when {
      "CheckLoginDetailsControllerService has found the details" in {
        mockCheckLoginDetailsSuccess(testRequestId, testLoginDetails)

        val result = TestCheckLoginDetailsController.checkLoginDetails(testRequestId)(testPostRequest)

        status(result) mustBe NO_CONTENT
      }
    }

    "return Forbidden" when {
      "CheckLoginDetailsControllerService has found the details and the details do not match" in {
        mockCheckLoginDetailsDoNotMatchFailure(testRequestId, testLoginDetails)

        val result = TestCheckLoginDetailsController.checkLoginDetails(testRequestId)(testPostRequest)

        status(result) mustBe FORBIDDEN
      }
    }

    "return Not Found" when {
      "CheckLoginDetailsControllerService has not found the details" in {
        mockCheckLoginDetailsNotFoundFailure(testRequestId, testLoginDetails)

        val result = TestCheckLoginDetailsController.checkLoginDetails(testRequestId)(testPostRequest)

        status(result) mustBe NOT_FOUND
      }
    }

    "return Internal Server Error" when {
      "CheckLoginDetailsControllerService fails" in {
        mockCheckLoginDetailsDatabaseFailure(testRequestId, testLoginDetails)

        val result = TestCheckLoginDetailsController.checkLoginDetails(testRequestId)(testPostRequest)

        status(result) mustBe INTERNAL_SERVER_ERROR
      }
    }

    "return Bad request" when {
      "controller receives invalid JSON" in {
        val testPostRequest: FakeRequest[JsValue] = FakeRequest(POST, "/sign-up/check-login-details").withBody(Json.obj())

        val result = TestCheckLoginDetailsController.checkLoginDetails(testRequestId)(testPostRequest)

        status(result) mustBe BAD_REQUEST
      }
    }
  }

}
