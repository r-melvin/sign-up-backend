package models

import play.api.libs.json._

case class UserDetailsModel(id: String, firstName: String, lastName: String, loginDetails: LoginDetailsModel)

object UserDetailsModel {

  implicit val reads: Reads[UserDetailsModel] = (json: JsValue) => for {
    id <- (json \ "_id").validate[String]
    firstName <- (json \ "firstName").validate[String]
    lastName <- (json \ "lastName").validate[String]
    email <- (json \ "email").validate[String]
    hashedPassword <- (json \ "hashedPassword").validate[String]
  } yield {
    UserDetailsModel(id, firstName, lastName, LoginDetailsModel(email, hashedPassword))
  }

  implicit val writes: OWrites[UserDetailsModel] = (userDetails: UserDetailsModel) => Json.obj(
    "_id" -> userDetails.id,
    "firstName" -> userDetails.firstName,
    "lastName" -> userDetails.lastName,
    "email" -> userDetails.loginDetails.email,
    "hashedPassword" -> userDetails.loginDetails.hashedPassword
  )
}
