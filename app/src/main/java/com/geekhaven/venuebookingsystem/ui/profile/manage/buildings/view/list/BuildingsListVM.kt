package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.api.models.BuildingResponse
import com.geekhaven.venuebookingsystem.config.ui.AlertDialogConfig
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.SearchBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Building
import com.geekhaven.venuebookingsystem.models.data.toBuilding
import com.geekhaven.venuebookingsystem.models.data.toBuildingResponse
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class BuildingsListVM: AbsFragmentVM() {

  private lateinit var buildingsList: List<Building>
  private lateinit var displayList: MutableLiveData<List<Building>>

  fun getBuildingsList(): LiveData<List<Building>> = displayList
  private fun setBuildingsList(buildings: List<Building>) { displayList.value = buildings }

  override fun onFragmentStart() {
    displayList = MutableLiveData()

    mainVM.setAppBarConfig(AppBarConfig(
      titleBarConfig = TitleBarConfig(
        showBackButton = true,
        title = "View Buildings List",
      ),
      searchBarConfig = SearchBarConfig(
        hint = "Search Buildings",
        showFilterButton = false,
      )
    ))

    loadBuildingsList()

    launchTask {
      mainVM.getSearchTextFlow().collect { updateBuildingsBySearch(it) }
    }
  }

  private fun loadBuildingsList(){
    launchCatchingTaskWithLoader(
      task = { apiRepo.getAllBuildings() },
      success = { handleListLoaded(it) }
    )
  }

  private fun handleListLoaded(responses: List<BuildingResponse>?){
    buildingsList = responses?.map { it.toBuilding() } ?: emptyList()
    updateBuildingsBySearch("")
  }

  private fun updateBuildingsBySearch(name: String) {
    buildingsList
      .filter { it.name.lowercase().startsWith(name.lowercase())}
      .let { launchTaskInMain{ setBuildingsList(it) } }
  }

  fun handleBuildingSelect(id: String){
    mainVM.selectedBuildingId = id
    sendNavAction(R.id.action_buildingsListFragment_to_viewBuildingFragment)
  }

  fun handleBuildingEditSelect(id: String) {
    mainVM.selectedBuildingId = id
    sendNavAction(R.id.action_buildingsListFragment_to_editBuildingFragment)
  }

  fun handleBuildingRemoveSelect(id: String) {
    val building = buildingsList.filter { it.id == id }[0]
    setAlertDialogConfig(AlertDialogConfig(
      title = "Venue Booking System",
      message = "Are you sure you want to delete ${building.name}",
      onYes = { removeBuilding(building) }
    ))
    showAlertDialog()
  }

  private fun removeBuilding(building: Building) {
    launchCatchingTaskWithLoader(
      task = { apiRepo.removeBuilding(building.toBuildingResponse()) },
      success = { loadBuildingsList() }
    )
  }

}
