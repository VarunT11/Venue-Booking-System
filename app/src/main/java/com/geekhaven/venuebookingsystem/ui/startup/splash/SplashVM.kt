package com.geekhaven.venuebookingsystem.ui.startup.splash

import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM
import kotlinx.coroutines.delay

class SplashVM : AbsFragmentVM() {

  override fun onFragmentStart() {
    mainVM.setAppBarConfig(AppBarConfig())
    handleSplashScreen()
  }

  private fun handleSplashScreen() = launchTask {
    delay(500)

    if (isUserSignedIn()) handleLoggedInUser()
    else navigateToLoginFragment()
  }

  private fun isUserSignedIn(): Boolean = firebaseRepo.isUserSignedIn()

  private fun handleLoggedInUser() = launchCatchingTaskWithLoader(
    task = { mainVM.loadUserDetails() },
    success = { sendNavAction(R.id.action_splashFragment_to_homeFragment) },
    failure = { signOutUser() }
  )

  private fun signOutUser() = launchCatchingTaskWithLoader(
    task = { firebaseRepo.signOut() },
    success = { navigateToLoginFragment() },
  )

  private fun navigateToLoginFragment() { sendNavAction(R.id.action_splashFragment_to_loginFragment) }

}
