package utils

import java.util.UUID

import models.{LoginDetailsModel, UserDetailsModel}

object TestConstants {

  val testRequestId = UUID.randomUUID().toString

  val testFirstName = UUID.randomUUID().toString

  val testLastName = UUID.randomUUID().toString

  val testEmail = UUID.randomUUID().toString

  val testPassword = UUID.randomUUID().toString

  val testLoginDetails = LoginDetailsModel(testEmail, testPassword)

  val testUserDetails = UserDetailsModel(testFirstName, testLastName, testLoginDetails)

  val testInvalidLoginDetails = LoginDetailsModel("qwertyu", "qwertyuiop")

}
