package com.geekhaven.venuebookingsystem.ui.home.change_time

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.DatePickerConfig
import com.geekhaven.venuebookingsystem.config.ui.TimePickerConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Booking
import com.geekhaven.venuebookingsystem.models.data.Venue
import com.geekhaven.venuebookingsystem.models.data.toBooking
import com.geekhaven.venuebookingsystem.models.data.toVenue
import com.geekhaven.venuebookingsystem.models.type.BookingStatus
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM
import com.geekhaven.venuebookingsystem.utils.getDurationInMinute
import com.geekhaven.venuebookingsystem.utils.toInstantString
import java.util.Calendar

class ChangeTimeVM: AbsFragmentVM() {

    private lateinit var venueDetails: MutableLiveData<Venue>
    private lateinit var buildingName: MutableLiveData<String>
    private lateinit var bookingDate: MutableLiveData<Calendar>
    private lateinit var bookingsList: MutableLiveData<List<Booking>>

    fun getVenueDetails(): LiveData<Venue> = venueDetails
    fun getBuildingName(): LiveData<String> = buildingName
    fun getBookingDate(): LiveData<Calendar> = bookingDate
    fun getBookingsList(): LiveData<List<Booking>> = bookingsList

    private fun updateVenueDetails(venue: Venue) { venueDetails.value = venue }
    private fun updateBuildingName(name: String) { buildingName.value = name }
    private fun updateBookingDate(date: Calendar) { bookingDate.value = date }
    private fun updateBookingsList(list: List<Booking>) { bookingsList.value = list }

    override fun onFragmentStart() {
        venueDetails = MutableLiveData()
        buildingName = MutableLiveData()
        bookingDate = MutableLiveData()
        bookingsList = MutableLiveData()

        mainVM.setAppBarConfig(AppBarConfig(
            titleBarConfig = TitleBarConfig(
                title = "Change Event Time",
                showBackButton = true
            )
        ))

        loadDetails()
    }

    private fun loadDetails() {
        val booking = mainVM.initiateBookingDetails ?: return

        launchTaskInMain {
            booking
                .venueId
                ?.let { apiRepo.getVenueDetails(it) }?.toVenue()
                ?.also {
                    updateVenueDetails(it)
                    it.buildingId?.let { id -> apiRepo.getBuildingDetails(id) }
                        ?.let { building -> updateBuildingName(building.name) }
                }

            updateBookingDate(booking.eventStartTime)
            loadBookingsList()
        }
    }

    private fun loadBookingsList() {
        val currentBooking = mainVM.initiateBookingDetails ?: return
        val eventDate = bookingDate.value ?: return

        launchTaskInMain {
            val responses = apiRepo.getBookingsByVenueDay(currentBooking.venueId!!, eventDate.toInstantString())
            println(responses)
            val bookings = responses
                ?.map { it.toBooking() }
                ?.filter { it.bookingStatus == BookingStatus.Approved || it.bookingStatus == BookingStatus.Pending }
                ?: emptyList()

            updateBookingsList(ArrayList(bookings).apply { add(currentBooking) })
        }
    }

    fun changeDate() {
        setDatePickerConfig(DatePickerConfig(
            title = "Select a Date",
            defaultSelection = bookingDate.value!!.timeInMillis,
            onSelect = {
                bookingDate.value = Calendar.getInstance().apply { timeInMillis = it }
                loadBookingsList()
            }
        ))
    }

    fun changeTime() {
        val currentBooking = mainVM.initiateBookingDetails ?: return

        val startTime = currentBooking.eventStartTime
        val endTime = currentBooking.eventEndTime

        selectNewStartTime(startTime, endTime)
    }

    private fun selectNewStartTime(startTime: Calendar, endTime: Calendar) {
        setTimePickerConfig(TimePickerConfig(
            title = "Select New Start Time",
            hour = startTime.get(Calendar.HOUR_OF_DAY),
            minute = startTime.get(Calendar.MINUTE),
            onSelect = { selectNewEndTime(endTime, it.timeInMillis) }
        ))
    }

    private fun selectNewEndTime(endTime: Calendar, newStartTime: Long) {
        setTimePickerConfig(TimePickerConfig(
            title = "Select New End Time",
            hour = endTime.get(Calendar.HOUR_OF_DAY),
            minute = endTime.get(Calendar.MINUTE),
            onSelect = { updateNewTime(newStartTime, it.timeInMillis) }
        ))
    }

    private var updatedBooking: Booking? = null

    private fun updateNewTime(newStartTime: Long, newEndTime: Long){
        val startTime = Calendar.getInstance().apply { timeInMillis = newStartTime }
        val endTime = Calendar.getInstance().apply { timeInMillis = newEndTime }

        val bookingDate = bookingDate.value!!

        startTime.apply {
            set(Calendar.DAY_OF_MONTH, bookingDate.get(Calendar.DAY_OF_MONTH))
            set(Calendar.MONTH, bookingDate.get(Calendar.MONTH))
            set(Calendar.YEAR, bookingDate.get(Calendar.YEAR))
        }

        endTime.apply {
            set(Calendar.DAY_OF_MONTH, bookingDate.get(Calendar.DAY_OF_MONTH))
            set(Calendar.MONTH, bookingDate.get(Calendar.MONTH))
            set(Calendar.YEAR, bookingDate.get(Calendar.YEAR))
        }

        val eventDuration = getDurationInMinute(startTime, endTime)
        if(eventDuration < 0) {
            mainVM.showToastMessage("End Time should be ahead of start time")
            return
        }
        if(eventDuration <= 15) {
            mainVM.showToastMessage("End Time should be atleast 15 minutes ahead of Start Time")
            return
        }

        val currentBooking = mainVM.initiateBookingDetails ?: return
        val updatedBooking = currentBooking.copy(
            eventStartTime = startTime,
            eventEndTime = endTime,
            eventDuration = eventDuration,
        )

        val bookings = bookingsList.value ?: return
        updateBookingsList(ArrayList(bookings.filter { it.id!=null }).apply { add(updatedBooking) })
        this.updatedBooking = updatedBooking
    }

    fun saveChanges() {
        updatedBooking?.let { mainVM.initiateBookingDetails = updatedBooking }
        sendNavAction(-1)
    }

}
