package com.geekhaven.venuebookingsystem.ui.profile.manage.venues.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.models.items.ManageOptionItem
import com.geekhaven.venuebookingsystem.adapter.models.type.ManageOptionType
import com.geekhaven.venuebookingsystem.adapter.models.type.ManageOptionType.Add
import com.geekhaven.venuebookingsystem.adapter.models.type.ManageOptionType.View
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class ManageVenuesHomeVM: AbsFragmentVM() {

  private lateinit var optionsList: MutableLiveData<ArrayList<ManageOptionItem>>
  fun getOptionsList(): LiveData<ArrayList<ManageOptionItem>> = optionsList
  private fun setOptionsList(options: ArrayList<ManageOptionItem>) { optionsList.value = options }

  override fun onFragmentStart() {
    optionsList = MutableLiveData()

    mainVM.setAppBarConfig(AppBarConfig(
      titleBarConfig = TitleBarConfig(
        title = "Manage Venues",
        showBackButton = false
      )
    ))

    setOptionsList(ArrayList(listOf(
      ManageOptionItem(Add, "Add a new Venue"),
      ManageOptionItem(View, "View Venues List")
    )))
  }

  fun handleOptionSelected(type: ManageOptionType){
    when(type){
      is Add -> { sendNavAction(R.id.action_manageVenuesHomeFragment_to_addVenueFragment) }
      is View -> { sendNavAction(R.id.action_manageVenuesHomeFragment_to_venueListFragment) }
    }
  }

}
