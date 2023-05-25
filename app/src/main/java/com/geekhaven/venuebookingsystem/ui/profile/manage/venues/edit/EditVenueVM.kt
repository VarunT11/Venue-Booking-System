package com.geekhaven.venuebookingsystem.ui.profile.manage.venues.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.api.models.BuildingResponse
import com.geekhaven.venuebookingsystem.api.models.UserResponse
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Building
import com.geekhaven.venuebookingsystem.models.data.User
import com.geekhaven.venuebookingsystem.models.data.Venue
import com.geekhaven.venuebookingsystem.models.data.toBuilding
import com.geekhaven.venuebookingsystem.models.data.toUser
import com.geekhaven.venuebookingsystem.models.data.toVenue
import com.geekhaven.venuebookingsystem.models.data.toVenueResponse
import com.geekhaven.venuebookingsystem.models.type.VenueType
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class EditVenueVM: AbsFragmentVM() {

    private lateinit var currentVenueDetails: MutableLiveData<Venue>
    private lateinit var buildingsList: MutableLiveData<List<Building>>
    private lateinit var venueTypeList: MutableLiveData<List<VenueType>>
    private lateinit var usersList: MutableLiveData<List<User>>

    private lateinit var inputNameError: MutableLiveData<String?>
    private lateinit var inputBuildingError: MutableLiveData<String?>
    private lateinit var inputFloorError: MutableLiveData<String?>
    private lateinit var inputVenueTypeError: MutableLiveData<String?>
    private lateinit var inputAuthorityError: MutableLiveData<String?>
    private lateinit var inputSeatingCapacityError: MutableLiveData<String?>

    private var selectedBuilding: Building? = null
    private var selectedVenueType: VenueType? = null
    private var selectedUser: User? = null

    fun getCurrentVenueDetails(): LiveData<Venue> = currentVenueDetails
    fun getBuildingsList(): LiveData<List<Building>> = buildingsList
    fun getVenueTypeList(): LiveData<List<VenueType>> = venueTypeList
    fun getUsersList(): LiveData<List<User>> = usersList

    fun getInputNameError(): LiveData<String?> = inputNameError
    fun getInputBuildingError(): LiveData<String?> = inputBuildingError
    fun getInputFloorError(): LiveData<String?> = inputFloorError
    fun getInputVenueTypeError(): LiveData<String?> = inputVenueTypeError
    fun getInputAuthorityError(): LiveData<String?> = inputAuthorityError
    fun getInputSeatingCapacity(): LiveData<String?> = inputSeatingCapacityError

    fun setSelectedBuilding(position: Int) {
        selectedBuilding =
            if(position == -1) null
            else buildingsList.value?.get(position)
    }

    fun setSelectedVenueType(position: Int) {
        selectedVenueType =
            if(position == -1) null
            else venueTypeList.value?.get(position)
    }

    fun setSelectedUser(position: Int) {
        selectedUser =
            if(position == -1) null
            else usersList.value?.get(position)
    }

    private fun setCurrentVenueDetails(venue: Venue) { currentVenueDetails.value = venue }
    private fun setBuildingsList(buildings: List<Building>) { buildingsList.value = buildings }
    private fun setVenueTypeList(venueTypes: List<VenueType>) { venueTypeList.value = venueTypes }
    private fun setUsersList(users: List<User>) { usersList.value = users }

    private fun setInputNameError(error: String?) { inputNameError.value = error }
    private fun setInputBuildingError(error: String?) { inputBuildingError.value = error}
    private fun setInputFloorError(error: String?) { inputFloorError.value = error }
    private fun setInputVenueTypeError(error: String?) { inputVenueTypeError.value = error }
    private fun setInputAuthorityError(error: String?) { inputAuthorityError.value = error }
    private fun setSeatingCapacityError(error: String?) { inputSeatingCapacityError.value = error }

    override fun onFragmentStart() {
        currentVenueDetails = MutableLiveData()
        buildingsList = MutableLiveData()
        venueTypeList = MutableLiveData()
        usersList = MutableLiveData()

        inputNameError = MutableLiveData()
        inputBuildingError = MutableLiveData()
        inputFloorError = MutableLiveData()
        inputVenueTypeError = MutableLiveData()
        inputAuthorityError = MutableLiveData()
        inputSeatingCapacityError = MutableLiveData()

        mainVM.setAppBarConfig(
            AppBarConfig(
            titleBarConfig = TitleBarConfig(
                showBackButton = true,
                title = "Edit Venue Details"
            )
        )
        )

        setVenueTypeList(listOf(
            VenueType.Classroom,
            VenueType.Lab,
            VenueType.MeetingRoom,
            VenueType.Auditorium,
            VenueType.Other
        ))

        loadBuildingsAndUsers()
    }

    private fun loadBuildingsAndUsers() {
        launchCatchingTaskWithLoader(
            task = {apiRepo.getAllBuildings()},
            success = {handleBuildingsLoaded(it)}
        )
    }

    private fun handleBuildingsLoaded(buildings: List<BuildingResponse>?){
        buildings
            ?.map { it.toBuilding() }
            ?.let { setBuildingsList(it) }

        launchCatchingTaskWithLoader(
            task = { apiRepo.getAllUsers() },
            success = {handleUsersLoaded(it)}
        )
    }

    private fun handleUsersLoaded(users: List<UserResponse>?) {
        users
            ?.map { it.toUser() }
            ?.let { setUsersList(it) }
        loadVenueDetails()
    }

    private fun loadVenueDetails() {
        mainVM.selectedVenueId?.let {
            launchCatchingTaskWithLoader(
                task = { apiRepo.getVenueDetails(it) },
                success = {
                    it?.let { setCurrentVenueDetails(it.toVenue()) }
                }
            )
        }
    }

    fun handleInputNameChanged() { setInputNameError(null) }
    fun handleInputBuildingChanged() { setInputBuildingError(null) }
    fun handleInputFloorChanged() { setInputFloorError(null) }
    fun handleInputVenueTypeChanged() { setInputVenueTypeError(null) }
    fun handleInputAuthorityChanged() { setInputAuthorityError(null) }
    fun handleSeatingCapacityChanged() { setSeatingCapacityError(null) }

    fun maybeUpdateVenue(
        name: String,
        floor: String,
        capacity: String,
        isAccessible: Boolean,
        hasAirConditioner: Boolean,
        hasProjector: Boolean,
        hasSpeaker: Boolean,
        hasWhiteboard: Boolean,
    ) {
        if(!validateInput(name, selectedBuilding, floor, selectedVenueType, selectedUser, capacity)) return
        updateVenue(name, selectedBuilding!!, floor.toInt(), selectedVenueType!!, selectedUser!!, capacity.toInt(), isAccessible, hasAirConditioner, hasProjector, hasSpeaker, hasWhiteboard)
    }

    private fun validateInput(
        name: String,
        building: Building?,
        floor: String,
        type: VenueType?,
        authority: User?,
        capacity: String
    ): Boolean {
        var valid = true

        if(name.isEmpty()) {
            setInputNameError("Please enter a valid Name")
            valid = false
        }

        if(building == null) {
            setInputBuildingError("Please select a building")
            valid = false
        }

        if(floor.isEmpty() || floor.toInt()<0){
            setInputFloorError("Please enter a valid Floor number")
            valid = false
        }

        if(type == null) {
            setInputVenueTypeError("Please select a Venue Type")
            valid = false
        }

        if(authority == null) {
            setInputAuthorityError("Please select the Venue Authority")
            valid = false
        }

        if(capacity.isEmpty() || capacity.toInt() < 0) {
            setSeatingCapacityError("Please enter a valid Seating Capacity")
            valid = false
        }

        return  valid
    }

    private fun updateVenue(
        name: String,
        building: Building,
        floor: Int,
        type: VenueType,
        authority: User,
        capacity: Int,
        isAccessible: Boolean,
        hasAirConditioner: Boolean,
        hasProjector: Boolean,
        hasSpeaker: Boolean,
        hasWhiteboard: Boolean,
    ) {
        val venue = currentVenueDetails.value!!.copy(
            name = name,
            buildingId = building.id,
            floorNumber = floor,
            venueType = type,
            authorityId = authority.email,
            seatingCapacity = capacity,
            isAccessible = isAccessible,
            hasAirConditioner = hasAirConditioner,
            hasProjectors = hasProjector,
            hasSpeakers = hasSpeaker,
            hasWhiteboard = hasWhiteboard,
        )

        launchCatchingTaskWithLoader(
            task = { apiRepo.updateVenue(venue.toVenueResponse()) },
            success = {
                mainVM.showToastMessage("Venue updated successfully")
                sendNavAction(-1)
            }
        )
    }

}
