package controllers

import play.api.libs.json.Json
import play.api.test.Helpers._
import utils.IntegrationTestConstants.{testUserDetailsModel, testRequestId}
import utils.{ComponentSpecBase, TestAccountsRepository}

class StoreUserDetailsControllerISpec extends ComponentSpecBase with TestAccountsRepository {

  "storeUserDetails" should {
    "store the supplied user details in mongo" in {
      val res = post(
        uri = s"/store-user-details/$testRequestId"
      )(Json.toJson(testUserDetailsModel))

      res must have {
        httpStatus(NO_CONTENT)
      }

      val databaseRecord = await(accountsRepositoryRepo.findById(testRequestId))

      databaseRecord must contain(Json.toJsObject(testUserDetailsModel))
    }
  }

}
