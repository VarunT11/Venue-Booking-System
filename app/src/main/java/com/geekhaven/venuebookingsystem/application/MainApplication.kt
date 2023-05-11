package com.geekhaven.venuebookingsystem.application

import android.app.Application
import com.geekhaven.venuebookingsystem.api.ApiClient
import com.geekhaven.venuebookingsystem.api.ApiHandler
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth

class MainApplication: Application() {

  private lateinit var _mAuth: FirebaseAuth
  private lateinit var _signInClient: SignInClient
  private lateinit var _apiHandler: ApiHandler

  override fun onCreate() {
    super.onCreate()
    initComponents()
  }

  private fun initComponents(){
    _mAuth = FirebaseAuth.getInstance()
    _signInClient = Identity.getSignInClient(this)
    _apiHandler = ApiClient.apiHandler
  }

  val mAuth: FirebaseAuth
    get() = _mAuth

  val signInClient: SignInClient
    get() = _signInClient

  val apiHandler
    get() = _apiHandler
}
