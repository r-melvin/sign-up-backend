package controllers

import play.api.http.Status._
import play.api.libs.json.Json
import repositories.AccountsRepository
import utils.IntegrationSpecBase
import utils.IntegrationTestConstants._

class StoreUserDetailsControllerISpec extends IntegrationSpecBase {

  lazy val repo: AccountsRepository = app.injector.instanceOf[AccountsRepository]

  "storeUserDetails" should {
    "store the supplied user details in mongo" in {

      val res = post(
        uri = s"/store-user-details/$testId"
      )(Json.toJson(testUserDetailsModel))

      res must have {
        httpStatus(NO_CONTENT)
      }

      val databaseRecord = await(repo.findById(testId))

      databaseRecord must contain {
        Json.obj("_id" -> testId) ++
        Json.toJsObject(testUserDetailsModel)
      }

    }
  }

}
