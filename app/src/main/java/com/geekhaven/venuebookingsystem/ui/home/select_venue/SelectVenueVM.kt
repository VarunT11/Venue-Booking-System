package com.geekhaven.venuebookingsystem.ui.home.select_venue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.models.items.SelectVenueListItem
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.SearchBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Venue
import com.geekhaven.venuebookingsystem.models.data.toVenue
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class SelectVenueVM: AbsFragmentVM() {

    private lateinit var venuesList: List<Venue>

    private lateinit var displayList: MutableLiveData<List<SelectVenueListItem>>
    fun getDisplayList(): LiveData<List<SelectVenueListItem>> = displayList
    private fun updateDisplayList(list: List<SelectVenueListItem>) { displayList.value = list }

    override fun onFragmentStart() {
        displayList = MutableLiveData()

        mainVM.setAppBarConfig(AppBarConfig(
            titleBarConfig = TitleBarConfig(
                showBackButton = true,
                title = "Select Venue"
            ),
            searchBarConfig = SearchBarConfig(
                hint = "Search Venues"
            )
        ))

        launchTaskInMain {
            mainVM.getSearchTextFlow().collect {
                updateVenuesBySearch(it)
            }
        }

        loadVenuesList()
    }

    private fun loadVenuesList() {
        launchCatchingTaskWithLoader(
            task = { apiRepo.getAllVenues() },
            success = { responses ->
                responses?.map { it.toVenue() }?.let { venuesList = it }
                updateVenuesBySearch("")
            }
        )
    }

    private fun updateVenuesBySearch(name: String) {
        launchTaskInMain {
            venuesList
                .filter { it.venueType == mainVM.initiateBookingVenueType }
                .filter { it.name.lowercase().startsWith(name.lowercase()) }
                .map { SelectVenueListItem(
                    id = it.id!!,
                    name = it.name,
                    buildingName = getBuildingName(it.buildingId!!),
                    venueType = it.venueType.toDisplayString(),
                    capacity = it.seatingCapacity.toString()
                ) }
                .let { updateDisplayList(it) }
        }
    }

    private suspend fun getBuildingName(id: String): String {
        return apiRepo.getBuildingDetails(id)?.name ?: ""
    }

    fun selectVenue(id: String) {
        mainVM.initiateBookingDetails = mainVM.initiateBookingDetails!!.copy(venueId = id)
        sendNavAction(R.id.action_selectVenueFragment_to_reviewBookingFragment)
    }

}
