package com.geekhaven.venuebookingsystem.ui.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.toUserResponse
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class EditProfileVM: AbsFragmentVM() {

  private lateinit var userName: MutableLiveData<String>
  private lateinit var inputNameError: MutableLiveData<String?>

  fun getUserName(): LiveData<String> = userName
  fun getInputNameError(): LiveData<String?> = inputNameError

  private fun updateUserName(name: String) { userName.value = name }
  private fun updateInputNameError(error: String?) { inputNameError.value = error }

  override fun onFragmentStart() {
    userName = MutableLiveData()
    inputNameError = MutableLiveData()

    mainVM.setAppBarConfig(
      AppBarConfig(
        titleBarConfig = TitleBarConfig(
          title = "Edit Profile",
          titleIconRes = R.drawable.ic_edit_round,
          showBackButton = true,
        )
      )
    )

    mainVM.getCurrentUserDetails().value?.name?.let { updateUserName(it) }
  }

  fun maybeUpdateUserName(name: String) {
    if (name.isNotEmpty()) updateUserDetails(name)
    else updateInputNameError("Please enter a valid name")
  }

  private fun updateUserDetails(name: String) {
    launchCatchingTaskWithLoader(
      task = { updateActualUserDetails(name) },
      success = { handleUpdateSuccess() },
      loaderMessage = "Updating Details"
    )
  }

  private suspend fun updateActualUserDetails(name: String) {
    mainVM.getCurrentUserDetails().value?.copy(name = name)?.toUserResponse()
      ?.let { apiRepo.updateUser(it) }
      ?.also { mainVM.updateCurrentUserDetails(it) }
  }

  private fun handleUpdateSuccess() {
    sendNavAction(-1)
    mainVM.showToastMessage("User Details Updated")
  }

  fun handleInputNameChanged() { updateInputNameError(null) }

}
