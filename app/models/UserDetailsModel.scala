package models

import play.api.libs.json._

case class UserDetailsModel(firstName: String, lastName: String, loginDetails: LoginDetailsModel)

object UserDetailsModel {

  implicit val reads: Reads[UserDetailsModel] = (json: JsValue) => for {
    firstName <- (json \ "firstName").validate[String]
    lastName <- (json \ "lastName").validate[String]
    email <- (json \ "email").validate[String]
    hashedPassword <- (json \ "hashedPassword").validate[String]
  } yield {
    UserDetailsModel(firstName, lastName, LoginDetailsModel(email, hashedPassword))
  }

  implicit val writes: OWrites[UserDetailsModel] = (userDetails: UserDetailsModel) => Json.obj(
    "email" -> userDetails.loginDetails.email,
    "firstName" -> userDetails.firstName,
    "lastName" -> userDetails.lastName,
    "hashedPassword" -> userDetails.loginDetails.hashedPassword
  )
}
