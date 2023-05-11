package com.geekhaven.venuebookingsystem.repository

import com.geekhaven.venuebookingsystem.api.ApiHandler
import com.geekhaven.venuebookingsystem.api.models.UserResponse
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth

class MainRepo(apiHandler: ApiHandler, mAuth: FirebaseAuth, signInClient: SignInClient) {

  val firebaseRepo = FirebaseRepo(mAuth, signInClient)
  val apiRepo = ApiRepo(apiHandler)

  suspend fun refreshToken() {
    firebaseRepo.getIdToken().takeIf { it != "" }
      ?.also { apiRepo.updateAuthToken(it) }
  }

  suspend fun getCurrentUserResponseDetails(): UserResponse = firebaseRepo
    .getCurrentUserEmail()
    ?.let { apiRepo.getUserDetails(it) }
    ?: throw Exception("Some Error Occurred")

  suspend fun signIn(credentials: String) {
    apiRepo.loginUsingCredentials(credentials)
    firebaseRepo.loginUsingCredentials(credentials)
  }

}
