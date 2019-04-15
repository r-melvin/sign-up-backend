package models

import play.api.libs.json.{Json, OFormat}

case class UserDetailsModel(firstName: String, lastName: String, email: String, hashedPassword: String)

object UserDetailsModel {
  implicit val format: OFormat[UserDetailsModel] = Json.format[UserDetailsModel]
}
