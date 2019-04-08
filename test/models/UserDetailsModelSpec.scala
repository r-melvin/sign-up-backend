package models

import org.scalatestplus.play.PlaySpec
import play.api.libs.json._
import utils.TestConstants._

class UserDetailsModelSpec extends PlaySpec {

  "UserDetailsModel" should {

    val testJson = Json.parse(
      """
        |{
        |   "firstName": "Rodney",
        |   "lastName": "Trotter",
        |   "email": "rodney.trotter@trotter-independent-trading.com"
        |}
      """.stripMargin
    )

    "contain three fields" in {
      testUserDetails mustEqual UserDetailsModel("Rodney", "Trotter", "rodney.trotter@trotter-independent-trading.com")
    }

    "write to json" in {
      val json = Json.toJson(testUserDetails)

      json mustEqual testJson
    }

    "read to model" in {
      val userDetails = Json.fromJson[UserDetailsModel](testJson)

      userDetails mustEqual JsSuccess(testUserDetails)
    }
  }

}