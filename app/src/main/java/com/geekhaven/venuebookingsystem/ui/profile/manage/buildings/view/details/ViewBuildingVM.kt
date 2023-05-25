package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.config.ui.AlertDialogConfig
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Building
import com.geekhaven.venuebookingsystem.models.data.toBuilding
import com.geekhaven.venuebookingsystem.models.data.toBuildingResponse
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class ViewBuildingVM: AbsFragmentVM() {

  private lateinit var buildingDetails: MutableLiveData<Building>
  fun getBuildingDetails():LiveData<Building> = buildingDetails
  private fun updateBuildingDetails(building: Building) { buildingDetails.value = building }

  override fun onFragmentStart() {
    buildingDetails = MutableLiveData()

    mainVM.setAppBarConfig(
      AppBarConfig(
        titleBarConfig = TitleBarConfig(
          showBackButton = true,
          title = "Building Details"
        )
      )
    )

    loadBuildingDetails()
  }

  private fun loadBuildingDetails() {
    mainVM.selectedBuildingId?.let {
      launchCatchingTaskWithLoader(
        task = { apiRepo.getBuildingDetails(it) },
        success = {
          response -> response?.let { updateBuildingDetails(response.toBuilding()) }
        }
      )
    }
  }

  fun editBuildingDetails() {
    mainVM.selectedBuildingId = buildingDetails.value!!.id
    sendNavAction(R.id.action_viewBuildingFragment_to_editBuildingFragment)
  }

  fun maybeRemoveBuilding() {
    setAlertDialogConfig(
      AlertDialogConfig(
        title = "Warning",
        message = "Are you sure you want to delete this building",
        onYes = { deleteBuilding() }
      )
    )
    showAlertDialog()
  }

  private fun deleteBuilding() {
    val building = buildingDetails.value!!
    launchCatchingTaskWithLoader(
      task = { apiRepo.removeBuilding(building.toBuildingResponse()) },
      success = {
        mainVM.showToastMessage("Building removed successfully")
        sendNavAction(-1)
      }
    )
  }

}
