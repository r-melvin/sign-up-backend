package models

import play.api.libs.json.{Json, OFormat}

case class LoginDetailsModel(email: String,
                             hashedPassword: String
                            )

object LoginDetailsModel { implicit val format: OFormat[LoginDetailsModel] = Json.format[LoginDetailsModel] }
