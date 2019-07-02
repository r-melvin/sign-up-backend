package models

import play.api.libs.json._
import utils.TestConstants._
import utils.UnitSpec

class LoginDetailsModelSpec extends UnitSpec {

  "LoginDetailsModel" should {

    val testJson = Json.obj(
      "email" -> testEmail,
      "hashedPassword" -> testPassword
    )

    "contain 3 fields" in {
      testLoginDetails mustEqual LoginDetailsModel(testEmail, testPassword)
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
