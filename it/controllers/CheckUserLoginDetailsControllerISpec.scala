package controllers

import models.LoginDetailsModel
import play.api.libs.json.{JsSuccess, Json}
import play.api.test.Helpers._
import utils.IntegrationTestConstants.{testLoginDetails, testRequestId, testUserDetails}
import utils.{ComponentSpecBase, TestAccountsRepository}

class CheckUserLoginDetailsControllerISpec extends ComponentSpecBase with TestAccountsRepository {

  "checkLoginDetails" should {
    "return NO_CONTENT" when {
      "provided details match those stored in mongo" in {
        await(accountsRepo.insert(testUserDetails.loginDetails.email, Json.toJsObject(testUserDetails)))

        val res = post(s"/check-login-details")(Json.toJsObject(testLoginDetails))

        res must have(
          httpStatus(NO_CONTENT),
          emptyBody
        )

        val databaseRecord = await(accountsRepo.findById(testUserDetails.loginDetails.email))
        Json.fromJson[LoginDetailsModel](databaseRecord.get) mustEqual JsSuccess(testLoginDetails)
      }
    }
  }

}
