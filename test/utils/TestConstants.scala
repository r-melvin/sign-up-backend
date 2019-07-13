package utils

import java.util.UUID

import models.{LoginDetailsModel, UserDetailsModel}

object TestConstants {

  val testRequestId: String = UUID.randomUUID().toString

  val testFirstName: String = UUID.randomUUID().toString

  val testLastName: String = UUID.randomUUID().toString

  val testEmail: String = UUID.randomUUID().toString

  val testPassword: String = UUID.randomUUID().toString

  val testLoginDetails: LoginDetailsModel = LoginDetailsModel(testEmail, testPassword)

  val testUserDetails: UserDetailsModel = UserDetailsModel(testRequestId, testFirstName, testLastName, testLoginDetails)

}
