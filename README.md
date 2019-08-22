# sign-up-backend

# Completed APIs
POST /sign-up/store-user-details/
  - Endpoint to store users details in Mongo when they register through the frontend

POST /sign-up/check-login-details/
  - Endpoint which checks that the user's email and password exist in Mongo

# Not Implemented Yet
GET /sign-up/retrieve-user-details/
  - Endpoint to retrieve user details from Mongo for the account page in the frontend
  
POST /sign-up/amend-user-details/
  - Endpoint to update Mongo with amended user details
  
DELETE /sign-up/remove-user-details/
  - Endpoint to delete user details from Mongo

# About
This is a backend microservice built with Scala using the Play Framework & ReactiveMongo

To run locally, use command `sbt "run 9001"`
