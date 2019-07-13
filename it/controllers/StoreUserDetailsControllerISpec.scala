package controllers

import models.{LoginDetailsModel, UserDetailsModel}
import play.api.libs.json.{JsSuccess, Json}
import play.api.test.Helpers._
import utils.IntegrationTestConstants.{testRequestId, testUserDetails}
import utils.{ComponentSpecBase, TestAccountsRepository}

class StoreUserDetailsControllerISpec extends ComponentSpecBase with TestAccountsRepository {

  "storeUserDetails" should {
    "store the supplied user details in mongo" in {
      val idKey = "_id"
      val res = post(
        uri = s"/store-user-details/$testRequestId"
      )(Json.toJson(testUserDetails))

      res must have {
        httpStatus(CREATED)
      }

      val databaseRecord = await(accountsRepo.findById(testRequestId))
      Json.fromJson[UserDetailsModel](databaseRecord.get) mustEqual JsSuccess(testUserDetails)

    }
  }

}
