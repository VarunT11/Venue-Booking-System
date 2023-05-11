package com.geekhaven.venuebookingsystem.ui.startup.signin

import android.app.PendingIntent
import android.content.IntentSender
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.config.AppConfig
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.common.api.ApiException

class SignInVM: AbsFragmentVM() {

  private lateinit var signInPendingIntent: MutableLiveData<PendingIntent?>
  fun getSignInPendingIntent(): LiveData<PendingIntent?> = signInPendingIntent
  private fun sendPendingIntent(pendingIntent: PendingIntent) { signInPendingIntent.value = pendingIntent }

  override fun onFragmentStart() {
    signInPendingIntent = MutableLiveData()
    mainVM.setAppBarConfig(AppBarConfig())
  }

  fun signInWithGoogle(){
    val request = GetSignInIntentRequest.builder()
      .setServerClientId(AppConfig.SERVER_CLIENT_ID)
      .build()

    signInClient.getSignInIntent(request)
      .addOnSuccessListener { sendPendingIntent(it) }
      .addOnFailureListener {
        sendLog( "Sign-In Failed, Error: ${it.localizedMessage}")
        mainVM.showToastMessage("Error in Signing In")
      }
  }

  fun handleActivityResult(activityResult: ActivityResult) {
    try {
      val credential = signInClient.getSignInCredentialFromIntent(activityResult.data)
      sendLog("Credential obtained successfully, now passing it to Server for verification")
      signInUserWithCredentials(credential.googleIdToken ?: "")
    } catch (e: ApiException) {
      sendLog( "Sign-In Failed, Error: ${e.localizedMessage}")
      when {
        e.statusCode == 13 -> mainVM.showToastMessage("Sign-in Failed\nPlease use an Authorized Account")
        e.statusCode != 13 && e.statusCode != 16 -> mainVM.showToastMessage("Error in Signing-In")
      }
    }
  }

  fun handleSignInError(e: IntentSender.SendIntentException) {
    sendLog( "Sign-In Failed, Error: ${e.localizedMessage}")
    mainVM.showToastMessage("Error in Signing In")
  }

  private fun signInUserWithCredentials(credentials: String) {
    launchCatchingTaskWithLoader(
      task = { signInUser(credentials) },
      success = { sendNavAction(R.id.action_loginFragment_to_homeFragment) },
      loaderMessage = "Signing In",
    )
  }

  private suspend fun signInUser(credentials: String) {
    mainRepo.signIn(credentials)
    mainVM.loadUserDetails()
  }

}
