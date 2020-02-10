package models

import play.api.libs.json._

case class UserDetailsModel(firstName: String, lastName: String, loginDetails: LoginDetailsModel)

object UserDetailsModel {

  implicit val format: OFormat[UserDetailsModel] = Json.format[UserDetailsModel]

}
