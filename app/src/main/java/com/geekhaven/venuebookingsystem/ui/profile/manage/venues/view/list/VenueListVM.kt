package com.geekhaven.venuebookingsystem.ui.profile.manage.venues.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.models.items.VenueListItem
import com.geekhaven.venuebookingsystem.api.models.VenueResponse
import com.geekhaven.venuebookingsystem.config.ui.AlertDialogConfig
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.SearchBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Venue
import com.geekhaven.venuebookingsystem.models.data.toVenue
import com.geekhaven.venuebookingsystem.models.data.toVenueResponse
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class VenueListVM: AbsFragmentVM() {

    private lateinit var venuesList: List<Venue>
    private lateinit var displayList: MutableLiveData<List<VenueListItem>>

    fun getVenueList(): LiveData<List<VenueListItem>> = displayList
    private fun updateDisplayList(items: List<VenueListItem>) { displayList.value = items }

    override fun onFragmentStart() {
        displayList = MutableLiveData()

        mainVM.setAppBarConfig(AppBarConfig(
            titleBarConfig = TitleBarConfig(
                title = "View Venues List",
                showBackButton = true
            ),
            searchBarConfig = SearchBarConfig(
                hint = "Search Venues"
            )
        ))

        loadVenuesList()

        launchTask {
            mainVM.getSearchTextFlow().collect { updateVenuesBySearch(it) }
        }
    }

    private fun loadVenuesList() {
        launchCatchingTaskWithLoader(
            task = { apiRepo.getAllVenues() },
            success = { handleListLoaded(it) }
        )
    }

    private fun handleListLoaded(responses: List<VenueResponse>?) {
        venuesList = responses?.map { it.toVenue() } ?: emptyList()
        updateVenuesBySearch("")
    }

    private fun updateVenuesBySearch(name: String) {
        launchTaskInMain {
            venuesList
                .filter { it.name.lowercase().startsWith(name.lowercase()) }
                .map {
                    VenueListItem(
                        id = it.id!!,
                        name = it.name,
                        buildingName = getBuildingNameFromId(it.buildingId)
                    )
                }
                .let { updateDisplayList(it) }
        }

    }

    private suspend fun getBuildingNameFromId(id: String?): String {
        return id
            ?.let { apiRepo.getBuildingDetails(id) }
            ?.name
            ?: id
            ?: ""
    }

    fun handleUserSelect(id: String) {
        mainVM.selectedVenueId = id
        sendNavAction(R.id.action_venueListFragment_to_venueDetailsFragment)
    }

    fun handleEditSelect(id: String) {
        mainVM.selectedVenueId = id
        sendNavAction(R.id.action_venueListFragment_to_editVenueFragment)
    }

    fun handleRemoveSelect(id: String) {
        val venue = venuesList.filter { it.id == id }[0]
        setAlertDialogConfig(
            AlertDialogConfig(
                title = "Warning",
                message = "Are you sure you want to remove ${venue.name}?",
                onYes = { removeVenue(venue) }
            )
        )
        showAlertDialog()
    }

    private fun removeVenue(venue: Venue) {
        launchCatchingTaskWithLoader(
            task = { apiRepo.removeVenue(venue.toVenueResponse()) },
            success = {
                mainVM.showToastMessage("Venue removed successfully")
                loadVenuesList()
            }
        )
    }
}
