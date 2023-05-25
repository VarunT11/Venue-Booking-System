package com.geekhaven.venuebookingsystem.ui.profile.manage.users.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.models.items.UserListItem
import com.geekhaven.venuebookingsystem.api.models.UserResponse
import com.geekhaven.venuebookingsystem.config.ui.AlertDialogConfig
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.SearchBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.User
import com.geekhaven.venuebookingsystem.models.data.toUser
import com.geekhaven.venuebookingsystem.models.data.toUserResponse
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM
import com.geekhaven.venuebookingsystem.utils.getRolesText

class UsersListVM : AbsFragmentVM() {

    private lateinit var usersList: List<User>
    private lateinit var displayList: MutableLiveData<List<UserListItem>>

    fun getUsersList(): LiveData<List<UserListItem>> = displayList
    private fun updateUsersList(users: List<UserListItem>) { displayList.value = users }

    override fun onFragmentStart() {
        displayList = MutableLiveData()

        mainVM.setAppBarConfig(
            AppBarConfig(
                titleBarConfig = TitleBarConfig(
                    showBackButton = true,
                    title = "View Users List",
                ), searchBarConfig = SearchBarConfig(
                    hint = "Search Users",
                )
            )
        )

        loadUsersList()

        launchTask {
            mainVM.getSearchTextFlow().collect { updateUsersBySearch(it) }
        }
    }

    private fun loadUsersList() {
        launchCatchingTaskWithLoader(
            task = { apiRepo.getAllUsers() },
            success = { handleListLoaded(it) }
        )
    }

    private fun handleListLoaded(responses: List<UserResponse>?) {
        usersList = responses?.map { it.toUser() } ?: emptyList()
        updateUsersBySearch("")
    }

    private fun updateUsersBySearch(name: String) {
        usersList
            .filter { it.name.lowercase().startsWith(name.lowercase()) }
            .map { UserListItem(it.email, it.name, getRolesText(it.isAdmin, it.isAuthority), getAuthorityNameFromEmail(it.parentEmail)) }
            .let { launchTaskInMain{ updateUsersList(it) } }
    }

    private fun getAuthorityNameFromEmail(email: String?): String {
        return usersList
            .filter { it.email == email }
            .takeIf { it.isNotEmpty() }
            ?.get(0)?.name
            ?: email
            ?: ""
    }

    fun handleUserSelect(id: String) {
        mainVM.selectedUserId = id
        sendNavAction(R.id.action_usersListFragment_to_userDetailsFragment)
    }

    fun handleUserEditSelect(id: String) {
        mainVM.selectedUserId = id
        sendNavAction(R.id.action_usersListFragment_to_editUserFragment)
    }

    fun handleUserRemoveSelect(id: String) {
        val user = usersList.filter { it.email == id }[0]
        setAlertDialogConfig(
            AlertDialogConfig(
                title = "Warning",
                message = "Are you sure you want to remove ${user.name}",
                onYes = { removeUser(user) }
            )
        )
        showAlertDialog()
    }

    private fun removeUser(user: User) {
        launchCatchingTaskWithLoader(
            task = { apiRepo.removeUser(user.toUserResponse()) },
            success = {
                mainVM.showToastMessage("User removed successfully")
                loadUsersList()
            }
        )
    }

}
