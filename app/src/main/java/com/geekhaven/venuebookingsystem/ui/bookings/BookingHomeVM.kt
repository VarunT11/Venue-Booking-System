package com.geekhaven.venuebookingsystem.ui.bookings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingHomeRequestListItem
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Booking
import com.geekhaven.venuebookingsystem.models.data.toBooking
import com.geekhaven.venuebookingsystem.models.data.toBookingRequest
import com.geekhaven.venuebookingsystem.models.data.toVenue
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM
import com.geekhaven.venuebookingsystem.utils.toDateString

class BookingHomeVM: AbsFragmentVM() {

  private lateinit var bookingDetails: MutableLiveData<Booking>
  private lateinit var locationDetails: MutableLiveData<String>
  private lateinit var requestDisplayList: MutableLiveData<List<BookingHomeRequestListItem>>

  fun getBookingDetails(): LiveData<Booking> = bookingDetails
  fun getLocationDetails(): LiveData<String> = locationDetails
  fun getRequestDisplayList(): LiveData<List<BookingHomeRequestListItem>> = requestDisplayList

  private fun updateBookingDetails(booking: Booking) { bookingDetails.value = booking }
  private fun updateLocationDetails(location: String) { locationDetails.value = location }
  private fun updateRequestDisplayList(requests: List<BookingHomeRequestListItem>) { requestDisplayList.value = requests }

  override fun onFragmentStart() {
    bookingDetails = MutableLiveData()
    locationDetails = MutableLiveData()
    requestDisplayList = MutableLiveData()

    mainVM.setAppBarConfig(AppBarConfig(
      titleBarConfig = TitleBarConfig(
        title = "View Booking Details",
        showBackButton = true
      )
    ))

    loadBookingDetails()
  }

  private fun loadBookingDetails() {
    val bookingId = mainVM.selectedBookingId ?: return

    launchCatchingTaskWithLoader(
      task = { apiRepo.getBooking(bookingId) },
      success = { it?.toBooking()?.let {booking -> handleBookingDetailsLoaded(booking) } }
    )
  }

  private fun handleBookingDetailsLoaded(booking: Booking) {
    updateBookingDetails(booking)
    launchTaskInMain{ updateLocationDetails(getLocationDetails(booking.venueId!!)) }
    launchTaskInMain {
      val requests = apiRepo.getBookingRequestsByBooking(booking.id!!)?.map { it.toBookingRequest() } ?: return@launchTaskInMain

      requests.map {
        val user = apiRepo.getUserDetails(it.receiverId)?.name ?: it.receiverId
        BookingHomeRequestListItem(
          id = it.id,
          userName = user,
          requestStatus = it.requestStatus,
          lastUpdatedTime = it.lastUpdatedTime.toDateString(),
        )
      }
        .let { updateRequestDisplayList(it) }
    }
  }

  private suspend fun getLocationDetails(venueId: String): String {
    val venue = apiRepo.getVenueDetails(venueId)?.toVenue()!!
    val building = apiRepo.getBuildingDetails(venue.buildingId!!)!!
    return "${venue.name}, ${building.name}"
  }

}
