package controllers

import javax.inject._
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class StoreUserDetailsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def storeUserDetails(): Action[AnyContent] = Action.async { implicit request =>
    Future.successful(
      NotImplemented
    )
  }
}
