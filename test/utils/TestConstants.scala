package utils

import models.{LoginDetailsModel, UserDetailsModel}

object TestConstants {

  val testFirstName = "Rodney"

  val testLastName = "Trotter"

  val testEmail = "rodney.trotter@trotter-independent-trading.com"

  val testHashedPassword = "1234567"

  val testUserDetails = UserDetailsModel(testFirstName, testLastName, testEmail, testHashedPassword)

  val testLoginDetails = LoginDetailsModel(testEmail, testHashedPassword)

}
