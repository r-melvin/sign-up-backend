package repositories

import play.api.libs.json.Json
import play.api.test.Helpers._
import utils.IntegrationTestConstants._
import utils.{ComponentSpecBase, TestAccountsRepository}

import scala.concurrent.ExecutionContext.Implicits.global

class AccountsRepositoryISpec extends ComponentSpecBase with TestAccountsRepository {

  val idKey = "_id"

  val email: String = testUserDetails.loginDetails.email

  "insert" should {
    "successfully insert a model" in {
      val res = for {
        _ <- accountsRepo.insert(email, Json.toJsObject(testUserDetails))
        model <- accountsRepo.findById(email)
      } yield model

      await(res) must contain(
        Json.obj(idKey -> email) ++
        Json.toJsObject(testUserDetails)
      )
    }
  }

  //TODO: test update and findById methods

  "delete" should {
    "delete the entry stored against the id" in {
      val res = for {
        _ <- accountsRepo.insert(email, Json.toJsObject(testUserDetails))
        inserted <- accountsRepo.findById(email)
        _ <- accountsRepo.delete(email)
        postDelete <- accountsRepo.findById(email)
      } yield (inserted, postDelete)

      val (inserted, postDelete) = await(res)
      inserted must contain(
        Json.obj(idKey -> email) ++
        Json.toJsObject(testUserDetails)
      )
      postDelete mustBe None
    }
  }

}