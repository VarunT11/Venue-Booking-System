package com.geekhaven.venuebookingsystem.ui.profile.manage.users.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.User
import com.geekhaven.venuebookingsystem.models.data.toUserResponse
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM
import com.geekhaven.venuebookingsystem.utils.isValidEmail

class AddUserVM: AbsFragmentVM() {

  private lateinit var inputEmailError: MutableLiveData<String?>
  private lateinit var inputNameError: MutableLiveData<String?>
  private lateinit var inputParentEmailError: MutableLiveData<String?>

  fun getInputEmailError(): LiveData<String?> = inputEmailError
  fun getInputNameError(): LiveData<String?> = inputNameError
  fun getInputParentEmailError(): LiveData<String?> = inputParentEmailError

  private fun updateInputEmailError(error: String?) { inputEmailError.value = error }
  private fun updateInputNameError(error: String?) { inputNameError.value = error }
  private fun updateInputParentEmailError(error: String?) { inputParentEmailError.value = error }

  override fun onFragmentStart() {
    inputEmailError = MutableLiveData()
    inputNameError = MutableLiveData()
    inputParentEmailError = MutableLiveData()

    mainVM.setAppBarConfig(AppBarConfig(
      titleBarConfig = TitleBarConfig(
        showBackButton = true,
        title = "Add a new User",
      )
    ))

  }

  fun maybeAddUser(email: String, name: String, parentEmail: String, requirePermission: Boolean, isAdmin: Boolean) {
    if(!validateDetails(email, name, parentEmail)) return
    addNewUser(email, name, parentEmail, requirePermission, isAdmin)
  }

  private fun validateDetails(email: String, name: String, parentEmail: String): Boolean {
    var valid = true

    if(!isValidEmail(email)){
      updateInputEmailError("Please enter a valid email")
      valid = false
    }

    if(name.isEmpty()){
      updateInputNameError("Please enter a valid name")
      valid = false
    }

    if(!isValidEmail(parentEmail)){
      updateInputParentEmailError("Please enter a valid parent email")
      valid = false
    }

    return valid
  }

  private fun addNewUser(email: String, name: String, parentEmail: String, requirePermission: Boolean, isAdmin: Boolean){
    val user = User(
      email = email,
      name = name,
      parentEmail = parentEmail,
      requireParentPermission = requirePermission,
      isAdmin = isAdmin,
      isAuthority = false
    )

    launchCatchingTaskWithLoader(
      task = { apiRepo.addNewUser(user.toUserResponse()) },
      success = { handleAddSuccess() }
    )
  }

  private fun handleAddSuccess(){
    mainVM.showToastMessage("User added successfully")
    sendNavAction(-1)
  }

  fun handleInputEmailChanged() { updateInputEmailError(null) }
  fun handleInputNameChanged() { updateInputNameError(null) }
  fun handleInputParentEmailChanged() { updateInputParentEmailError(null) }

}
