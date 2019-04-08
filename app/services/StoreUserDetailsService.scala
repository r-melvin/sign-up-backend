package services

import javax.inject.{Inject, Singleton}
import models.UserDetailsModel
import play.api.libs.json.Json
import play.api.mvc.Request
import repositories.AccountsRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class StoreUserDetailsService @Inject()(accountsRepository: AccountsRepository)(implicit ec: ExecutionContext) {

  import StoreUserDetailsService._

  def storeUserDetails(requestId: String,
                       userDetails: UserDetailsModel
                      )(implicit request: Request[_]): Future[Either[StoreUserDetailsFailure.type, UserDetailsStored.type]] = {

    accountsRepository.insert(
        Json.obj("_id" -> requestId) ++
        Json.toJsObject(userDetails)
    ) map {
      _ => Right(UserDetailsStored)
    } recover {
      case _ => Left(StoreUserDetailsFailure)
    }
  }

}

object StoreUserDetailsService {

  case object UserDetailsStored

  case object StoreUserDetailsFailure
}