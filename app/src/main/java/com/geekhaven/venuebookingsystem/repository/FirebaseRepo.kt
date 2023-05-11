package com.geekhaven.venuebookingsystem.repository

import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class FirebaseRepo(private val mAuth: FirebaseAuth, private val signInClient: SignInClient) {

  fun isUserSignedIn(): Boolean = mAuth.currentUser != null
  fun getCurrentUserEmail(): String? = mAuth.currentUser?.email

  suspend fun getIdToken(): String =
    mAuth.currentUser?.getIdToken(true)?.await()?.token ?: ""

  suspend fun loginUsingCredentials(credentials: String) {
    val firebaseCredential = GoogleAuthProvider.getCredential(credentials, null)
    mAuth.signInWithCredential(firebaseCredential).await()
  }

  suspend fun signOut() {
    mAuth.signOut()
    signInClient.signOut().await()
  }

  fun getCurrentUserImageUrl() = mAuth.currentUser?.photoUrl
}
