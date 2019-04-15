package utils

import java.util.UUID

import models.UserDetailsModel

object TestConstants {

  val testRequestId: String = UUID.randomUUID().toString

  val testFirstName = "Rodney"

  val testLastName = "Trotter"

  val testEmail = "rodney.trotter@trotter-independent-trading.com"

  val testHashedPassword = "1234567"

  val testUserDetails = UserDetailsModel(testFirstName, testLastName, testEmail, testHashedPassword)

}
