package com.geekhaven.venuebookingsystem.ui.profile.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.models.items.ProfileSettingsItem
import com.geekhaven.venuebookingsystem.adapter.models.type.SettingsType
import com.geekhaven.venuebookingsystem.adapter.models.type.SettingsType.*
import com.geekhaven.venuebookingsystem.api.models.UserResponse
import com.geekhaven.venuebookingsystem.config.ui.AlertDialogConfig
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.models.data.User
import com.geekhaven.venuebookingsystem.models.data.toUser
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class ProfileVM : AbsFragmentVM() {

  private lateinit var parentUserDetails: MutableLiveData<User>
  private lateinit var settingsList: MutableLiveData<List<ProfileSettingsItem>>

  fun getParentUserDetails(): LiveData<User> = parentUserDetails
  fun getSettingsList(): LiveData<List<ProfileSettingsItem>> = settingsList

  private fun updateParentUserDetails(userResponse: UserResponse?) {
    parentUserDetails.value = userResponse?.toUser()
  }

  private fun updateSettingsList(list: List<ProfileSettingsItem>) { settingsList.value = list }

  override fun onFragmentStart() {
    parentUserDetails = MutableLiveData()
    settingsList = MutableLiveData()

    mainVM.setAppBarConfig(AppBarConfig())

    updateSettingsList(createSettingsList())
  }

  private fun createSettingsList(): List<ProfileSettingsItem> {
    val user = mainVM.getCurrentUserDetails().value
    val settingsList = ArrayList<ProfileSettingsItem>()

    settingsList.add(ProfileSettingsItem(EditProfile, "Edit Profile"))

    if (user?.isAdmin == true) {
      settingsList.addAll(
        listOf(
          ProfileSettingsItem(ManageUsers, "Manage Users"),
          ProfileSettingsItem(ManageBuildings, "Manage Buildings"),
          ProfileSettingsItem(ManageVenues, "Manage Venues"),
        )
      )
    } else if(user?.isAuthority == true) {
      settingsList.add(ProfileSettingsItem(ManageVenues, "Manage your Venues"))
    }

    settingsList.addAll(
      listOf(
        ProfileSettingsItem(About, "About"),
        ProfileSettingsItem(Logout, "Logout")
      )
    )

    return settingsList
  }

  fun getRolesText(isAdmin: Boolean, isAuthority: Boolean): String {
    val rolesList = ArrayList<String>()
    if(isAdmin) rolesList.add("Admin")
    if(isAuthority) rolesList.add("Authority")
    return rolesList.joinToString(", ")
  }

  fun getParentUserDetails(email: String) {
    launchCatchingTaskWithLoader(
      task = { apiRepo.getUserDetails(email) },
      success = { updateParentUserDetails(it) }
    )
  }

  fun handleSettingsOptionSelected(type: SettingsType) {
    when(type) {
      is EditProfile -> sendNavAction(R.id.action_profileFragment_to_editProfileFragment)
      is ManageUsers -> sendNavAction(R.id.action_profileFragment_to_manageUsersHomeFragment)
      is ManageBuildings -> sendNavAction(R.id.action_profileFragment_to_manageBuildingsFragment)
      is ManageVenues -> sendNavAction(R.id.action_profileFragment_to_manageVenuesHomeFragment)
      is About -> sendNavAction(R.id.action_profileFragment_to_aboutFragment)
      is Logout -> maybeLogout()
    }
  }

  private fun maybeLogout(){
    setAlertDialogConfig(AlertDialogConfig(
      title = "Venue Booking System",
      message = "Are you sure you want to logout?",
      onYes = { handleLogoutRequest() }
    ))
    showAlertDialog()
  }

  private fun handleLogoutRequest(){
    launchCatchingTaskWithLoader(
      task = { firebaseRepo.signOut() },
      success = { mainVM.sendLogoutAction() },
      loaderMessage = "Signing Out"
    )
  }

}
