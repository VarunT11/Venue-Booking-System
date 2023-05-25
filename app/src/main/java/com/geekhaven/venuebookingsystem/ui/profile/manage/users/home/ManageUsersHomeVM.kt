package com.geekhaven.venuebookingsystem.ui.profile.manage.users.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.models.items.ManageOptionItem
import com.geekhaven.venuebookingsystem.adapter.models.type.ManageOptionType
import com.geekhaven.venuebookingsystem.adapter.models.type.ManageOptionType.Add
import com.geekhaven.venuebookingsystem.adapter.models.type.ManageOptionType.View
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.SearchBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class ManageUsersHomeVM: AbsFragmentVM() {

  private lateinit var optionsList: MutableLiveData<ArrayList<ManageOptionItem>>
  fun getOptionsList(): LiveData<ArrayList<ManageOptionItem>> = optionsList
  private fun setOptionsList(options: ArrayList<ManageOptionItem>) { optionsList.value = options }

  override fun onFragmentStart() {
    optionsList = MutableLiveData()

    mainVM.setAppBarConfig(AppBarConfig(
      titleBarConfig = TitleBarConfig(
        showBackButton = false,
        title = "Manage Users",
      )
    ))

    setOptionsList(ArrayList(listOf(
      ManageOptionItem(Add, "Add a new User"),
      ManageOptionItem(View, "View Users List")
    )))
  }

  fun handleOptionSelected(type: ManageOptionType){
    when(type){
      is Add -> { sendNavAction(R.id.action_manageUsersHomeFragment_to_addUsersFragment) }
      is View -> { sendNavAction(R.id.action_manageUsersHomeFragment_to_usersListFragment) }
    }
  }

}
