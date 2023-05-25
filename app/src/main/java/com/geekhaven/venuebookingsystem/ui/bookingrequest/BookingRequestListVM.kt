package com.geekhaven.venuebookingsystem.ui.bookingrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingListItem
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingRequestListItem
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.BookingRequest
import com.geekhaven.venuebookingsystem.models.data.toBooking
import com.geekhaven.venuebookingsystem.models.data.toBookingRequest
import com.geekhaven.venuebookingsystem.models.data.toVenue
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM
import com.geekhaven.venuebookingsystem.utils.toDateString
import com.geekhaven.venuebookingsystem.utils.toTimeString

class BookingRequestListVM: AbsFragmentVM() {

  private lateinit var bookingRequestsDisplayList: MutableLiveData<List<BookingRequestListItem>>
  fun getDisplayList(): LiveData<List<BookingRequestListItem>> = bookingRequestsDisplayList
  private fun updateDisplayList(displayList: List<BookingRequestListItem>) { bookingRequestsDisplayList.value = displayList }

  override fun onFragmentStart() {
    bookingRequestsDisplayList = MutableLiveData()

    mainVM.setAppBarConfig(AppBarConfig(
      titleBarConfig = TitleBarConfig(
        showBackButton = false,
        title = "Booking Requests"
      )
    ))

    loadRequestsList()
  }

  private fun loadRequestsList() {
    val userId = mainVM.getCurrentUserDetails().value?.email ?: return

    launchCatchingTaskWithLoader(
      task = { apiRepo.getBookingRequestsByReceiver(userId) },
      success = { it?.map {response -> response.toBookingRequest() }?.let { requests -> handleRequestsListLoaded(requests) } }
    )
  }

  private fun handleRequestsListLoaded(requests: List<BookingRequest>) {
    launchTaskInMain {
      requests.map {request ->
        val booking = apiRepo.getBooking(request.bookingId)?.toBooking()!!
        val user = apiRepo.getUserDetails(booking.userId)!!
        BookingRequestListItem(
          id = request.id,
          title = booking.title,
          bookingType = booking.bookingType.toDisplayString(),
          requestStatus = request.requestStatus,
          userName = user.name,
          location = getLocationDetails(booking.venueId!!),
          eventDate = "${booking.eventStartTime.toTimeString()}, ${booking.eventStartTime.toDateString()}"
        )
      }
        .let { updateDisplayList(it) }
    }
  }

  private suspend fun getLocationDetails(venueId: String): String {
    val venue = apiRepo.getVenueDetails(venueId)?.toVenue()!!
    val building = apiRepo.getBuildingDetails(venue.buildingId!!)!!
    return "${venue.name}, ${building.name}"
  }

  fun handleBookingRequestSelect(id: String) {
    mainVM.selectedBookingRequestId = id
    sendNavAction(R.id.action_bookingRequestListFragment_to_bookingRequestHomeFragment)
  }

}
