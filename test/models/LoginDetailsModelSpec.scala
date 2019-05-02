package models

import org.scalatestplus.play.PlaySpec
import play.api.libs.json._
import utils.TestConstants._

class LoginDetailsModelSpec extends PlaySpec {

  "LoginDetailsModel" should {

    val testJson = Json.parse(
      """
        |{
        |   "email": "rodney.trotter@trotter-independent-trading.com",
        |   "hashedPassword": "1234567"
        |}
      """.stripMargin
    )

    "contain 2 fields" in {
      testLoginDetails mustEqual LoginDetailsModel(testEmail, testHashedPassword)
    }

    "write to json" in {
      val json = Json.toJson(testLoginDetails)

      json mustEqual testJson
    }

    "read to model" in {
      val loginDetails = Json.fromJson[LoginDetailsModel](testJson)

      loginDetails mustEqual JsSuccess(testLoginDetails)
    }
  }

}
