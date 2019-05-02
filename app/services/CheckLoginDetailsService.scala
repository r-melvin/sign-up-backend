package services

import javax.inject.{Inject, Singleton}
import models.LoginDetailsModel
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.Request
import repositories.AccountsRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CheckLoginDetailsService @Inject()(accountsRepository: AccountsRepository)(implicit ec: ExecutionContext) {

  import CheckLoginDetailsService._

  def checkLoginDetails(loginDetails: LoginDetailsModel)(implicit request: Request[_]): Future[CheckLoginDetailsResponse] = {

    accountsRepository.findByField(Json.toJsObject(loginDetails)) map {
     _ => Right(LoginDetailsFound)
    } recover {
      case e: NoSuchElementException => Left(LoginDetailsNotFound)
      case _ => Left(DatabaseFailure)
    }
  }
}

object CheckLoginDetailsService {

  type CheckLoginDetailsResponse = Either[CheckLoginDetailsFailure, LoginDetailsFound.type]

  sealed trait CheckLoginDetailsFailure

  case object LoginDetailsFound

  case object LoginDetailsNotFound extends CheckLoginDetailsFailure

  case object DatabaseFailure extends CheckLoginDetailsFailure

}

