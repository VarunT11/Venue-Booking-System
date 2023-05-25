package com.geekhaven.venuebookingsystem.ui.profile.manage.venues.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.config.ui.AlertDialogConfig
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Venue
import com.geekhaven.venuebookingsystem.models.data.toVenue
import com.geekhaven.venuebookingsystem.models.data.toVenueResponse
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class VenueDetailsVM: AbsFragmentVM() {

    private lateinit var venueDetails: MutableLiveData<Venue>
    private lateinit var buildingName: MutableLiveData<String>
    private lateinit var authorityName: MutableLiveData<String>

    fun getVenueDetails(): LiveData<Venue> = venueDetails
    fun getBuildingName(): LiveData<String> = buildingName
    fun getAuthorityName(): LiveData<String> = authorityName

    private fun updateVenueDetails(venue: Venue) { venueDetails.value = venue }
    private fun updateBuildingName(name: String) { buildingName.value = name }
    private fun updateAuthorityName(name: String) { authorityName.value = name }

    override fun onFragmentStart() {
        venueDetails = MutableLiveData()
        buildingName = MutableLiveData()
        authorityName = MutableLiveData()

        mainVM.setAppBarConfig(AppBarConfig(
            titleBarConfig = TitleBarConfig(
                showBackButton = true,
                title = "View Venue Details"
            )
        ))

        loadVenueDetails()
    }

    private fun loadVenueDetails() {
        mainVM.selectedVenueId?.let {
            launchCatchingTaskWithLoader(
                task = { apiRepo.getVenueDetails(it) },
                success = {
                    it?.toVenue()
                        ?.also {venue -> updateVenueDetails(venue) }
                        ?.let {venue -> fetchBuildingAndAuthorityDetails(venue) }
                }
            )
        }
    }

    private fun fetchBuildingAndAuthorityDetails(venue: Venue) {
        launchCatchingTaskWithLoader(
            task = { apiRepo.getBuildingDetails(venue.buildingId!!) },
            success = {
                it?.let { updateBuildingName(it.name) }
                launchCatchingTaskWithLoader(
                    task = { apiRepo.getUserDetails(venue.authorityId!!) },
                    success = {user ->
                        user?.let { updateAuthorityName(user.name) }
                    }
                )
            }
        )
    }

    fun editVenueDetails() {
        mainVM.selectedVenueId = venueDetails.value!!.id
        sendNavAction(R.id.action_venueDetailsFragment_to_editVenueFragment)
    }

    fun maybeRemoveVenue() {
        setAlertDialogConfig(
            AlertDialogConfig(
                title = "Warning",
                message = "Are you sure you want to remove this venue?",
                onYes = { removeVenue() }
            )
        )
        showAlertDialog()
    }

    private fun removeVenue() {
        val venue = venueDetails.value!!

        launchCatchingTaskWithLoader(
            task = { apiRepo.removeVenue(venue.toVenueResponse()) },
            success = {
                mainVM.showToastMessage("Venue removed successfully")
                sendNavAction(-1)
            }
        )
    }
}
