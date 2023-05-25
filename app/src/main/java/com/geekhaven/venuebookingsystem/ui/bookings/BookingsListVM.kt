package com.geekhaven.venuebookingsystem.ui.bookings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingListItem
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Booking
import com.geekhaven.venuebookingsystem.models.data.toBooking
import com.geekhaven.venuebookingsystem.models.data.toVenue
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM
import com.geekhaven.venuebookingsystem.utils.toDateString
import com.geekhaven.venuebookingsystem.utils.toTimeString

class BookingsListVM: AbsFragmentVM() {

  private lateinit var bookingDisplayList: MutableLiveData<List<BookingListItem>>
  fun getDisplayList(): LiveData<List<BookingListItem>> = bookingDisplayList
  private fun updateDisplayList(displayList: List<BookingListItem>) { bookingDisplayList.value = displayList }

  override fun onFragmentStart() {
    bookingDisplayList = MutableLiveData()

    mainVM.setAppBarConfig(AppBarConfig(
      titleBarConfig = TitleBarConfig(
        showBackButton = false,
        title = "My Bookings"
      )
    ))

    loadBookingsList()
  }

  private fun loadBookingsList() {
    val userId = mainVM.getCurrentUserDetails().value?.email ?: return

    launchCatchingTaskWithLoader(
      task = { apiRepo.getBookingsByUser(userId) },
      success = { it?.map { bookingResponse -> bookingResponse.toBooking() }?.let { bookings ->  handleBookingListLoaded(bookings) } }
    )
  }

  private fun handleBookingListLoaded(bookings: List<Booking>) {
    launchTaskInMain {
      bookings.map {
        BookingListItem(
          id = it.id!!,
          title = it.title,
          bookingType = it.bookingType.toDisplayString(),
          bookingStatus = it.bookingStatus!!,
          location = getLocationDetails(it.venueId!!),
          eventDate = "${it.eventStartTime.toTimeString()}, ${it.eventStartTime.toDateString()}"
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

  fun handleBookingSelect(id: String) {
    mainVM.selectedBookingId = id
    sendNavAction(R.id.action_bookingsListFragment_to_bookingHomeFragment)
  }

}
