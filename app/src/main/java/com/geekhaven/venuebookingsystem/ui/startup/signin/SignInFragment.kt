package com.geekhaven.venuebookingsystem.ui.startup.signin

import android.app.PendingIntent
import android.content.IntentSender
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.geekhaven.venuebookingsystem.databinding.FragmentSigninBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class SignInFragment : AbsFragment<FragmentSigninBinding, SignInVM>() {

  override val fragmentName: String = SignInFragment::class.java.simpleName
  override val vmClass: Class<SignInVM> = SignInVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentSigninBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getSignInPendingIntent().observe(viewLifecycleOwner) { it?.let { handlePendingIntent(it) } }
  }

  override fun addViewListeners() { binding.btnGoogleSignIn.setOnClickListener { mVM.signInWithGoogle() } }

  private fun handlePendingIntent(it: PendingIntent) {
    try { IntentSenderRequest.Builder(it.intentSender).build().let { loginResultHandler.launch(it) } }
    catch (e: IntentSender.SendIntentException) { mVM.handleSignInError(e) }
  }

  private val loginResultHandler = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { mVM.handleActivityResult(it) }
}
