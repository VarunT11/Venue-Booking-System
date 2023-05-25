package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.models.items.ManageOptionItem
import com.geekhaven.venuebookingsystem.adapter.models.type.ManageOptionType
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class ManageBuildingsVM: AbsFragmentVM() {

  private lateinit var optionsList: MutableLiveData<ArrayList<ManageOptionItem>>
  fun getOptionsList(): LiveData<ArrayList<ManageOptionItem>> = optionsList
  private fun setOptionsList(options: ArrayList<ManageOptionItem>) { optionsList.value = options }

  override fun onFragmentStart() {
    optionsList = MutableLiveData()

    mainVM.setAppBarConfig(AppBarConfig(
      titleBarConfig = TitleBarConfig(
        title = "Manage Buildings",
        showBackButton = false,
      )
    ))

    setOptionsList(ArrayList(listOf(
      ManageOptionItem(ManageOptionType.Add, "Add a new Building"),
      ManageOptionItem(ManageOptionType.View, "View Buildings List")
    )))
  }

  fun handleOptionSelected(type: ManageOptionType){
    when(type){
      is ManageOptionType.Add -> { sendNavAction(R.id.action_manageBuildingsFragment_to_addBuildingFragment) }
      is ManageOptionType.View -> { sendNavAction(R.id.action_manageBuildingsFragment_to_buildingsListFragment) }
    }
  }
}
