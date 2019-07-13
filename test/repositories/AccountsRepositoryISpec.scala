package repositories

import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.libs.json.Json
import play.api.test.Helpers._
import utils.TestConstants._

import scala.concurrent.ExecutionContext.Implicits.global

class AccountsRepositoryISpec extends PlaySpec with GuiceOneServerPerSuite with BeforeAndAfterEach {

  val accountsRepositoryRepo: AccountsRepository = app.injector.instanceOf[AccountsRepository]

  override def beforeEach: Unit = {
    super.beforeEach()
    await(accountsRepositoryRepo.drop)
  }

  "insert" should {
    "successfully insert a model" in {
      val res = for {
        _ <- accountsRepositoryRepo.insert(testRequestId, Json.toJsObject(testUserDetails))
        model <- accountsRepositoryRepo.findById(testRequestId)
      } yield model

      await(res) must contain(
        Json.obj("_id" -> testRequestId) ++ Json.toJsObject(testUserDetails)
      )
    }
  }

  //TODO: test update and findById methods

  "delete" should {
    "delete the entry stored against the id" in {
      val res = for {
        _ <- accountsRepositoryRepo.insert(testRequestId, Json.toJsObject(testUserDetails))
        inserted <- accountsRepositoryRepo.findById(testRequestId)
        _ <- accountsRepositoryRepo.delete(testRequestId)
        postDelete <- accountsRepositoryRepo.findById(testRequestId)
      } yield (inserted, postDelete)

      val (inserted, postDelete) = await(res)
      inserted must contain(Json.obj("_id" -> testRequestId) ++ Json.toJsObject(testUserDetails))
      postDelete mustBe None
    }
  }

}