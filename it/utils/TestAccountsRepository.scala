package utils

import org.scalatest.BeforeAndAfterEach
import play.api.test.Helpers._
import repositories.AccountsRepository

trait TestAccountsRepository extends BeforeAndAfterEach {
  this: ComponentSpecBase =>
  val accountsRepositoryRepo: AccountsRepository = app.injector.instanceOf[AccountsRepository]

  override def beforeEach: Unit = {
    super.beforeEach()
    await(accountsRepositoryRepo.drop)
  }
}