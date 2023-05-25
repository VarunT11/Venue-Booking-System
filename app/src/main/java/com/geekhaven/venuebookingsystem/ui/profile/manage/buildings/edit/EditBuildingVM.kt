package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Building
import com.geekhaven.venuebookingsystem.models.data.toBuilding
import com.geekhaven.venuebookingsystem.models.data.toBuildingResponse
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class EditBuildingVM : AbsFragmentVM() {

    private lateinit var buildingDetails: MutableLiveData<Building?>
    private lateinit var inputNameError: MutableLiveData<String?>

    fun getBuilding(): LiveData<Building?> = buildingDetails
    fun getInputNameError(): LiveData<String?> = inputNameError

    private fun updateBuilding(building: Building?) { buildingDetails.value = building }
    private fun updateInputNameError(error: String?) { inputNameError.value = error }

    override fun onFragmentStart() {
        buildingDetails = MutableLiveData()
        inputNameError = MutableLiveData()

        mainVM.setAppBarConfig(
            AppBarConfig(
                titleBarConfig = TitleBarConfig(
                    title = "Edit Building Details",
                    showBackButton = true,
                )
            )
        )

        mainVM
            .selectedBuildingId
            ?.let {
                launchCatchingTaskWithLoader(
                    task = { apiRepo.getBuildingDetails(it) },
                    success = { building -> updateBuilding(building?.toBuilding()) }
                )
            }
    }

    fun maybeUpdateBuilding(name: String){
        if(name.isNotEmpty()) updateBuilding(name)
        else updateInputNameError("Please enter a valid name for the Building")
    }

    private fun updateBuilding(name: String){
        val buildingResponse = buildingDetails.value?.copy(name = name)?.toBuildingResponse() ?: return
        launchCatchingTaskWithLoader(
            task = { apiRepo.updateBuilding(buildingResponse) },
            success = { handleUpdateSuccess() }
        )
    }

    private fun handleUpdateSuccess() {
        mainVM.showToastMessage("Building updated successfully")
        sendNavAction(-1)
    }

    fun handleInputNameChanged() { updateInputNameError(null) }

}
