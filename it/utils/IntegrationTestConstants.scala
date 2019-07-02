package utils

import java.util.UUID

import models.{LoginDetailsModel, UserDetailsModel}

object IntegrationTestConstants {

  val testFirstName = UUID.randomUUID().toString

  val testLastName = UUID.randomUUID().toString

  val testId = UUID.randomUUID().toString

  val testEmail = UUID.randomUUID().toString

  val testPassword = UUID.randomUUID().toString

  val testLoginDetails = LoginDetailsModel(testEmail, testPassword)

  val testUserDetailsModel = UserDetailsModel(testFirstName, testLastName, testLoginDetails)

}
