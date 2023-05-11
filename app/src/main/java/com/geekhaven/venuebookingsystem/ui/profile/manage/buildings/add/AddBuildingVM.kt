package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Building
import com.geekhaven.venuebookingsystem.models.data.toBuildingResponse
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class AddBuildingVM: AbsFragmentVM() {

  private lateinit var inputNameError: MutableLiveData<String?>
  fun getInputNameError(): LiveData<String?> = inputNameError
  private fun updateInputNameError(error: String?) { inputNameError.value = error }

  override fun onFragmentStart() {
    inputNameError = MutableLiveData()

    mainVM.setAppBarConfig(
      AppBarConfig(
      titleBarConfig = TitleBarConfig(
        title = "Add a New Building",
        showBackButton = true,
      )
    )
    )
  }

  fun maybeAddBuilding(name: String){
    if(name.isNotEmpty()) addNewBuilding(name)
    else updateInputNameError("Please enter a valid name for the Building")
  }

  private fun addNewBuilding(name: String){
    val buildingResponse = Building(id = null, name = name).toBuildingResponse()
    launchCatchingTaskWithLoader(
      task = { apiRepo.addNewBuilding(buildingResponse) },
      success = { handleAddSuccess() }
    )
  }

  private fun handleAddSuccess() {
    mainVM.showToastMessage("Building added successfully")
    sendNavAction(-1)
  }

  fun handleInputNameChanged() { updateInputNameError(null) }

}
