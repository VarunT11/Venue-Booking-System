package com.geekhaven.venuebookingsystem.ui.home.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Booking
import com.geekhaven.venuebookingsystem.models.data.toBookingResponse
import com.geekhaven.venuebookingsystem.models.type.VenueType
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class ReviewBookingVM: AbsFragmentVM() {

    private lateinit var bookingDetails: MutableLiveData<Booking>
    private lateinit var venueType: MutableLiveData<VenueType>
    private lateinit var locationDetails: MutableLiveData<String>

    fun getBookingDetails(): LiveData<Booking> = bookingDetails
    fun getVenueType(): LiveData<VenueType> = venueType
    fun getLocationDetails(): LiveData<String> = locationDetails

    private fun updateBookingDetails(booking: Booking) { bookingDetails.value = booking }
    private fun updateVenueType(type: VenueType) { venueType.value = type }
    private fun updateLocationDetails(location: String) { locationDetails.value = location }

    override fun onFragmentStart() {
        bookingDetails = MutableLiveData()
        venueType = MutableLiveData()
        locationDetails = MutableLiveData()

        mainVM.setAppBarConfig(AppBarConfig(
            titleBarConfig = TitleBarConfig(
                showBackButton = true,
                title = "Review Booking"
            )
        ))

        loadBookingDetails()
    }

    private fun loadBookingDetails() {
        mainVM.initiateBookingDetails?.let { updateBookingDetails(it) }
        mainVM.initiateBookingVenueType?.let { updateVenueType(it) }
        loadLocationDetails()
    }

    private fun loadLocationDetails() {
        bookingDetails.value?.let {
            val venueId = it.venueId ?: return
            launchTaskInMain {
                val venue = apiRepo.getVenueDetails(venueId) ?: return@launchTaskInMain
                val building = apiRepo.getBuildingDetails(venue.buildingId!!)
                updateLocationDetails("${venue.name}, ${building?.name}")
            }
        }
    }

    fun confirmBooking() {
        val booking = bookingDetails.value ?: return
        launchCatchingTaskWithLoader(
            task = { apiRepo.addBooking(booking.toBookingResponse()) },
            success = { onBookingConfirm() }
        )
    }

    private fun onBookingConfirm() {
        mainVM.showToastMessage("Booking Created Successfully")
        sendNavAction(R.id.action_reviewBookingFragment_to_homeFragment)
    }

    fun changeEventTime() {
        sendNavAction(R.id.action_reviewBookingFragment_to_changeTimeFragment)
    }

}
