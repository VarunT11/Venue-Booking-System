package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.api.models.BuildingResponse
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.SearchBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Building
import com.geekhaven.venuebookingsystem.models.data.toBuilding
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

    launchCatchingTaskWithLoader(
      task = { apiRepo.getAllBuildings() },
      success = { handleListLoaded(it) }
    )

    launchTask {
      mainVM.getSearchTextFlow().collect { updateBuildingsBySearch(it) }
    }
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

  }

  fun handleBuildingEditSelect(id: String) {

  }

  fun handleBuildingRemoveSelect(id: String) {

  }

}
