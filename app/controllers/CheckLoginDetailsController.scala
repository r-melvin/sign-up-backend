package controllers

import javax.inject.{Inject, Singleton}
import models.LoginDetailsModel
import play.api.libs.json.{JsSuccess, JsValue}
import play.api.mvc._
import services.CheckLoginDetailsService
import services.CheckLoginDetailsService.{LoginDetailsFound, LoginDetailsNotFound}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CheckLoginDetailsController @Inject()(checkLoginDetailsService: CheckLoginDetailsService,
                                            val controllerComponents: ControllerComponents
                                           )(implicit ec: ExecutionContext) extends BaseController {

  def checkLoginDetails(): Action[JsValue] = Action.async(parse.json) {
    implicit request =>
      request.body.validate[LoginDetailsModel] match {
        case JsSuccess(loginDetails, _) =>
          checkLoginDetailsService.checkLoginDetails(loginDetails) map {
            case Right(LoginDetailsFound) => NoContent
            case Left(LoginDetailsNotFound) => NotFound
            case Left(_) => InternalServerError
          }
        case _ => Future.successful(BadRequest(request.body))
      }
  }
}
