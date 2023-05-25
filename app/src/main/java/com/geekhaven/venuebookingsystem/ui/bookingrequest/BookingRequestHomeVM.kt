package com.geekhaven.venuebookingsystem.ui.bookingrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingHomeRequestListItem
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Booking
import com.geekhaven.venuebookingsystem.models.data.BookingRequest
import com.geekhaven.venuebookingsystem.models.data.toBooking
import com.geekhaven.venuebookingsystem.models.data.toBookingRequest
import com.geekhaven.venuebookingsystem.models.data.toBookingRequestResponse
import com.geekhaven.venuebookingsystem.models.data.toVenue
import com.geekhaven.venuebookingsystem.models.type.BookingRequestStatus
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM
import com.geekhaven.venuebookingsystem.utils.toDateString

class BookingRequestHomeVM: AbsFragmentVM() {

  private lateinit var bookingDetails: MutableLiveData<Booking>
  private lateinit var bookingRequestStatus: MutableLiveData<BookingRequestStatus>
  private lateinit var locationDetails: MutableLiveData<String>
  private lateinit var requestDisplayList: MutableLiveData<List<BookingHomeRequestListItem>>

  fun getBookingDetails(): LiveData<Booking> = bookingDetails
  fun getBookingRequestStatus(): LiveData<BookingRequestStatus> = bookingRequestStatus
  fun getLocationDetails(): LiveData<String> = locationDetails
  fun getRequestDisplayList(): LiveData<List<BookingHomeRequestListItem>> = requestDisplayList

  private fun updateBookingDetails(booking: Booking) { bookingDetails.value = booking }
  private fun updateBookingRequestStatus(status: BookingRequestStatus) { bookingRequestStatus.value = status }
  private fun updateLocationDetails(location: String) { locationDetails.value = location }
  private fun updateRequestDisplayList(requests: List<BookingHomeRequestListItem>) { requestDisplayList.value = requests }

  override fun onFragmentStart() {
    bookingDetails = MutableLiveData()
    bookingRequestStatus = MutableLiveData()
    locationDetails = MutableLiveData()
    requestDisplayList = MutableLiveData()

    mainVM.setAppBarConfig(AppBarConfig(
      titleBarConfig = TitleBarConfig(
        showBackButton = true,
        title = "Booking Request Details"
      )
    ))

    loadBookingRequestDetails()
  }

  private var bookingRequest: BookingRequest? = null

  private fun loadBookingRequestDetails() {
    val requestId = mainVM.selectedBookingRequestId ?: return

    launchCatchingTaskWithLoader(
      task = { apiRepo.getBookingRequest(requestId) },
      success = { response -> response?.toBookingRequest()?.let { handleBookingRequestDetailsLoaded(it) } }
    )
  }

  private fun handleBookingRequestDetailsLoaded(bookingRequest: BookingRequest) {
    val bookingId = bookingRequest.bookingId
    this.bookingRequest = bookingRequest
    updateBookingRequestStatus(bookingRequest.requestStatus)

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

  fun approveBookingRequest() {
    bookingRequest
      ?.copy(requestStatus = BookingRequestStatus.Approved)
      ?.toBookingRequestResponse()
      ?.let { launchCatchingTaskWithLoader(
        task = { apiRepo.updateBookingRequest(it) },
        success = {
          loadBookingRequestDetails()
        }
      ) }
  }

  fun rejectBookingRequest() {
    bookingRequest
      ?.copy(requestStatus = BookingRequestStatus.Rejected)
      ?.toBookingRequestResponse()
      ?.let { launchCatchingTaskWithLoader(
        task = { apiRepo.updateBookingRequest(it) },
        success = {
          loadBookingRequestDetails()
        }
      ) }
  }

}
