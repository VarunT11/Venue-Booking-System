package com.geekhaven.venuebookingsystem.ui.profile.manage.users.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.User
import com.geekhaven.venuebookingsystem.models.data.toUser
import com.geekhaven.venuebookingsystem.models.data.toUserResponse
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM
import com.geekhaven.venuebookingsystem.utils.isValidEmail

class EditUserVM: AbsFragmentVM() {

    private lateinit var inputNameError: MutableLiveData<String?>
    private lateinit var inputParentEmailError: MutableLiveData<String?>
    private lateinit var userDetails: MutableLiveData<User>

    fun getInputNameError(): LiveData<String?> = inputNameError
    fun getInputParentEmailError(): LiveData<String?> = inputParentEmailError
    fun getUserDetails(): LiveData<User> = userDetails

    private fun updateInputNameError(error: String?) { inputNameError.value = error }
    private fun updateInputParentEmailError(error: String?) { inputParentEmailError.value = error }
    private fun updateUserDetails(user: User) { userDetails.value = user }

    override fun onFragmentStart() {
        inputNameError = MutableLiveData()
        inputParentEmailError = MutableLiveData()
        userDetails = MutableLiveData()

        mainVM.setAppBarConfig(
            AppBarConfig(
            titleBarConfig = TitleBarConfig(
                showBackButton = true,
                title = "Edit User Details",
            )
        )
        )

        loadUserDetails()
    }

    private fun loadUserDetails() {
        mainVM.selectedUserId
            ?.let {
              launchCatchingTaskWithLoader(
                  task = { apiRepo.getUserDetails(it) },
                  success = {
                      it?.let { user -> updateUserDetails(user.toUser()) }
                  }
              )
            }
    }

    fun maybeUpdateUser(
        name: String,
        parentEmail: String,
        requirePermission: Boolean,
        isAdmin: Boolean
    ) {
        if(!validateDetails(name, parentEmail)) return
        updateUser(name, parentEmail, requirePermission, isAdmin)
    }

    private fun validateDetails(name: String, parentEmail: String): Boolean {
        var valid = true

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

    private fun updateUser(
        name: String,
        parentEmail: String,
        requirePermission: Boolean,
        isAdmin: Boolean
    ){
        val user = userDetails.value?.copy(
            name = name,
            parentEmail = parentEmail,
            requireParentPermission = requirePermission,
            isAdmin = isAdmin
        )

        launchCatchingTaskWithLoader(
            task = { user?.let { apiRepo.updateUser(it.toUserResponse()) } },
            success = { handleUpdateSuccess() }
        )
    }

    private fun handleUpdateSuccess(){
        mainVM.showToastMessage("User added successfully")
        sendNavAction(-1)
    }

    fun handleInputNameChanged() { updateInputNameError(null) }
    fun handleInputParentEmailChanged() { updateInputParentEmailError(null) }

}
