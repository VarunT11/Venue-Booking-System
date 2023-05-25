package com.geekhaven.venuebookingsystem.ui.profile.manage.users.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.config.ui.AlertDialogConfig
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.User
import com.geekhaven.venuebookingsystem.models.data.toUser
import com.geekhaven.venuebookingsystem.models.data.toUserResponse
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class UserDetailsVM: AbsFragmentVM() {

    private lateinit var userDetails: MutableLiveData<User>
    fun getUserDetails(): LiveData<User> = userDetails
    private fun updateUserDetails(user: User) { userDetails.value = user }

    override fun onFragmentStart() {
        userDetails = MutableLiveData()

        mainVM.setAppBarConfig(
            AppBarConfig(
                titleBarConfig = TitleBarConfig(
                    title = "View User Details",
                    showBackButton = true
                )
            )
        )
        loadUserDetails()
    }

    private fun loadUserDetails() {
        mainVM.selectedUserId?.let {
            launchCatchingTaskWithLoader(
                task = { apiRepo.getUserDetails(it) },
                success = { it?.let { response -> updateUserDetails(response.toUser()) } }
            )
        }
    }

    fun editUserDetails() {
        mainVM.selectedUserId = userDetails.value!!.email
        sendNavAction(R.id.action_userDetailsFragment_to_editUserFragment)
    }

    fun maybeRemoveUser() {
        setAlertDialogConfig(AlertDialogConfig(
            title = "Warning",
            message = "Are you sure you want to remove this user",
            onYes = { removeUser() }
        ))
        showAlertDialog()
    }

    private fun removeUser() {
        val user = userDetails.value!!.toUserResponse()
        launchCatchingTaskWithLoader(
            task = { apiRepo.removeUser(user) },
            success = {
                mainVM.showToastMessage("User removed successfully")
                sendNavAction(-1)
            }
        )
    }

}
