package com.geekhaven.venuebookingsystem.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geekhaven.venuebookingsystem.api.ApiClient
import com.geekhaven.venuebookingsystem.api.ApiHandler
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth

class MainVMFactory(
  private val apiHandler: ApiHandler,
  private val mAuth: FirebaseAuth,
  private val signInClient: SignInClient,
) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MainVM::class.java)) {
      return MainVM(apiHandler, mAuth, signInClient) as T
    }
    throw IllegalArgumentException("Unknown Class")
  }
}
