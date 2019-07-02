# sign-up-backend

# APIs
POST /sign-up/store-user-details/:requestId 
  - Endpoint to store users details in Mongo when they register through the frontend
  
POST /sign-up/check-login-details/:requestId
  - Endpoint which checks that the user's email and password exist in Mongo
  
GET /sign-up/retrieve-user-details/:requestId
  - Endpoint to retrieve user details from Mongo for the account page in the frontend

PUT /sign-up/amend-user-details/:requestId
  - Endpoint to update Mongo with amended user details

DELETE /sign-up/remove-user-details/:requestId
  - Endpoint to delete user details from Mongo


# About
This is a backend microservice built with Scala using the Play Framework & ReactiveMongo

To run locally, use command `sbt "run 9001"`
