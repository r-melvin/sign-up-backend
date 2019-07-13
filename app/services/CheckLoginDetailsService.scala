package services

import javax.inject.{Inject, Singleton}
import models.LoginDetailsModel
import play.api.libs.json.Json
import play.api.mvc.Request
import repositories.AccountsRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CheckLoginDetailsService @Inject()(accountsRepository: AccountsRepository)(implicit ec: ExecutionContext) {

  import CheckLoginDetailsService._

  private def matchLoginDetails(enteredLoginDetails: LoginDetailsModel, retrievedLoginDetails: LoginDetailsModel): CheckLoginDetailsResponse = {
    if (retrievedLoginDetails == enteredLoginDetails) {
      Right(LoginDetailsMatch)
    }
    else {
      Left(LoginDetailsDoNotMatch)
    }
  }

  def checkLoginDetails(enteredLoginDetails: LoginDetailsModel)(implicit request: Request[_]): Future[CheckLoginDetailsResponse] = {

    accountsRepository.findById(enteredLoginDetails.email) collect {
      case Some(result) => matchLoginDetails(enteredLoginDetails, Json.fromJson[LoginDetailsModel](result).get)
    } recover {
      case e: NoSuchElementException => Left(LoginDetailsNotFound)
      case _ => Left(DatabaseFailure)
    }
  }

}

object CheckLoginDetailsService {

  type CheckLoginDetailsResponse = Either[CheckLoginDetailsFailure, LoginDetailsMatch.type]

  sealed trait CheckLoginDetailsFailure

  case object LoginDetailsMatch

  case object LoginDetailsDoNotMatch extends CheckLoginDetailsFailure

  case object LoginDetailsNotFound extends CheckLoginDetailsFailure

  case object DatabaseFailure extends CheckLoginDetailsFailure

}

