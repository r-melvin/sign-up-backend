package repositories

import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.libs.json.Json
import utils.TestConstants._
import utils.UnitSpec

import scala.concurrent.ExecutionContext.Implicits.global

class AccountsRepositoryISpec extends UnitSpec with GuiceOneServerPerSuite with BeforeAndAfterEach {
  lazy val repo: AccountsRepository = app.injector.instanceOf[AccountsRepository]

  override def beforeEach(): Unit = {
    super.beforeEach()
    await(repo.drop)
  }

  "insert" should {
    "successfully insert a model" in {
      val res = for {
        _ <- repo.insert(testRequestId, Json.toJsObject(testUserDetails))
        model <- repo.findById(testRequestId)
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
        _ <- repo.insert(testRequestId, Json.toJsObject(testUserDetails))
        inserted <- repo.findById(testRequestId)
        _ <- repo.delete(testRequestId)
        postDelete <- repo.findById(testRequestId)
      } yield (inserted, postDelete)

      val (inserted, postDelete) = await(res)
      inserted must contain(Json.obj("_id" -> testRequestId) ++ Json.toJsObject(testUserDetails))
      postDelete mustBe None
    }
  }

}