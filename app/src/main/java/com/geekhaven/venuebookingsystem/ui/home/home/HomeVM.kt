package com.geekhaven.venuebookingsystem.ui.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.DatePickerConfig
import com.geekhaven.venuebookingsystem.config.ui.TimePickerConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.models.data.Booking
import com.geekhaven.venuebookingsystem.models.data.Venue
import com.geekhaven.venuebookingsystem.models.type.BookingType
import com.geekhaven.venuebookingsystem.models.type.BookingType.*
import com.geekhaven.venuebookingsystem.models.type.BookingType.Other
import com.geekhaven.venuebookingsystem.models.type.VenueType
import com.geekhaven.venuebookingsystem.models.type.VenueType.*
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM
import com.geekhaven.venuebookingsystem.utils.getDurationInMinute
import java.util.Calendar

class HomeVM: AbsFragmentVM() {

  private var selectedBookingType: BookingType? = null
  private var selectedVenueType: VenueType? = null

  private lateinit var bookingTypeList: MutableLiveData<List<BookingType>>
  private lateinit var venueTypeList: MutableLiveData<List<VenueType>>
  private lateinit var eventDate: MutableLiveData<Calendar>
  private lateinit var startTime: MutableLiveData<Calendar>
  private lateinit var endTime: MutableLiveData<Calendar>

  private lateinit var inputTitleError: MutableLiveData<String?>
  private lateinit var inputDescriptionError: MutableLiveData<String?>
  private lateinit var inputDateError: MutableLiveData<String?>
  private lateinit var inputStartTimeError: MutableLiveData<String?>
  private lateinit var inputEndTimeError: MutableLiveData<String?>
  private lateinit var inputExpectedStrengthError: MutableLiveData<String?>
  private lateinit var inputBookingTypeError: MutableLiveData<String?>
  private lateinit var inputVenueTypeError: MutableLiveData<String?>

  fun getBookingTypeList(): LiveData<List<BookingType>> = bookingTypeList
  fun getVenueTypeList(): LiveData<List<VenueType>> = venueTypeList
  fun getEventDate(): LiveData<Calendar> = eventDate
  fun getStartTime(): LiveData<Calendar> = startTime
  fun getEndTime(): LiveData<Calendar> = endTime

  fun getInputTitleError(): LiveData<String?> = inputTitleError
  fun getInputDescriptionError(): LiveData<String?> = inputDescriptionError
  fun getInputDateError(): LiveData<String?> = inputDateError
  fun getInputStartTimeError(): LiveData<String?> = inputStartTimeError
  fun getInputEndTimeError(): LiveData<String?> = inputEndTimeError
  fun getInputExpectedStrengthError(): LiveData<String?> = inputExpectedStrengthError
  fun getInputBookingTypeError(): LiveData<String?> = inputBookingTypeError
  fun getInputVenueTypeError(): LiveData<String?> = inputVenueTypeError

  private fun updateBookingTypeList(list: List<BookingType>) { bookingTypeList.value = list }
  private fun updateVenueTypeList(list: List<VenueType>) { venueTypeList.value = list }
  private fun updateEventDate(date: Calendar) { eventDate.value = date }
  private fun updateStartTime(time: Calendar) { startTime.value = time }
  private fun updateEndTime(time: Calendar) { endTime.value = time }

  private fun updateInputTitleError(error: String?) { inputTitleError.value = error }
  private fun updateInputDescriptionError(error: String?) { inputDescriptionError.value = error }
  private fun updateInputDateError(error: String?) { inputDateError.value = error }
  private fun updateInputStartTimeError(error: String?) { inputStartTimeError.value = error }
  private fun updateInputEndTimeError(error: String?) { inputEndTimeError.value = error }
  private fun updateInputExpectedStrength(error: String?) { inputExpectedStrengthError.value = error }
  private fun updateInputBookingTypeError(error: String?) { inputBookingTypeError.value = error }
  private fun updateVenueTypeError(error: String?) { inputVenueTypeError.value = error }

  fun updateBookingType(position: Int) {
    val bookingType = bookingTypeList.value!![position]
    selectedBookingType = bookingType
  }

  fun updateVenueType(position: Int) {
    val venueType = venueTypeList.value!![position]
    selectedVenueType = venueType
  }

  override fun onFragmentStart() {
    bookingTypeList = MutableLiveData()
    venueTypeList = MutableLiveData()
    eventDate = MutableLiveData()
    startTime = MutableLiveData()
    endTime = MutableLiveData()

    inputTitleError = MutableLiveData()
    inputDescriptionError = MutableLiveData()
    inputDateError = MutableLiveData()
    inputStartTimeError = MutableLiveData()
    inputEndTimeError = MutableLiveData()
    inputExpectedStrengthError = MutableLiveData()
    inputVenueTypeError = MutableLiveData()
    inputBookingTypeError = MutableLiveData()

    mainVM.setAppBarConfig(
      AppBarConfig(
      titleBarConfig = TitleBarConfig(
        showBackButton = false,
        title = "Create new Booking"
      )
    )
    )

    updateBookingTypeList(listOf(Academic, Workshop, Event, Other))
    updateVenueTypeList(listOf(Classroom, Lab, MeetingRoom, Auditorium, VenueType.Other))
  }

  fun setEventDate() {
    setDatePickerConfig(DatePickerConfig(
      title = "Select Event Date",
      defaultSelection = eventDate.value?.timeInMillis ?: Calendar.getInstance().timeInMillis,
      onSelect = {
        Calendar.getInstance().apply { timeInMillis = it }
          .let { updateEventDate(it) }
      }
    ))
  }

  fun setStartTime() {
    setTimePickerConfig(TimePickerConfig(
      title = "Set Event Start Time",
      hour = startTime.value?.get(Calendar.HOUR_OF_DAY) ?: Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
      minute = startTime.value?.get(Calendar.MINUTE) ?: Calendar.getInstance().get(Calendar.MINUTE),
      onSelect = { updateStartTime(it) }
    ))
  }

  fun setEndTime() {
    setTimePickerConfig(TimePickerConfig(
      title = "Set Event End Time",
      hour = endTime.value?.get(Calendar.HOUR_OF_DAY) ?: Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
      minute = endTime.value?.get(Calendar.MINUTE) ?: Calendar.getInstance().get(Calendar.MINUTE),
      onSelect = { updateEndTime(it) }
    ))
  }

  fun maybeInitiateBooking(title: String, description: String, expectedStrength: String) {
    if(!validateDetails(title, description, eventDate.value, startTime.value, endTime.value, expectedStrength, selectedBookingType, selectedVenueType)) return
    else initiateBooking(title, description, expectedStrength.toInt(), selectedBookingType!!, selectedVenueType!!)
  }

  private fun validateDetails(
    title: String,
    description: String,
    eventDate: Calendar?,
    startTime: Calendar?,
    endTime: Calendar?,
    expectedStrength: String,
    bookingType: BookingType?,
    venueType: VenueType?
  ): Boolean {
    var valid = true

    if(title.isEmpty()){
      updateInputTitleError("Please enter a valid Title")
      valid = false
    }

    if(description.isEmpty()){
      updateInputDescriptionError("Please enter a valid Description")
      valid = false
    }

    if(eventDate == null){
      updateInputDateError("Please select the Event Date")
      valid = false
    } else if(eventDate < Calendar.getInstance()){
      updateInputDateError("Please choose an Upcoming Date")
      valid = false
    }

    if(startTime == null) {
      updateInputStartTimeError("Please select the Start Time")
      valid = false
    }

    if(endTime == null) {
      updateInputEndTimeError("Please select the End Time")
      valid = false
    }

    if(expectedStrength.isEmpty() || expectedStrength.toInt()<=0){
      updateInputExpectedStrength("Please enter the Expected Strength of the Event")
      valid = false
    }

    if(bookingType == null) {
      updateInputBookingTypeError("Please select the Booking Type")
      valid = false
    }

    if(venueType == null) {
      updateVenueTypeError("Please select the Venue Type")
      valid = false
    }

    if(eventDate!=null && startTime!=null && endTime!=null) {
      valid = validateStartAndEndTime(eventDate, startTime, endTime)
    }

    return valid
  }

  private fun initiateBooking(
    title: String,
    description: String,
    expectedStrength: Int,
    bookingType: BookingType,
    venueType: VenueType
  ) {
    val currentUserEmail = mainVM.getCurrentUserDetails().value?.email ?: return

    val booking = Booking(
      id = null,
      userId = currentUserEmail,
      venueId = null,
      bookingTime = null,
      eventStartTime = eventStartTime,
      lastUpdatedTime = null,
      bookingStatus = null,
      bookingType = bookingType,
      eventDuration = eventDuration,
      eventEndTime = eventEndTime,
      expectedStrength = expectedStrength,
      title = title,
      description = description
    )

    mainVM.initiateBookingDetails = booking
    mainVM.initiateBookingVenueType = venueType
    sendNavAction(R.id.action_homeFragment_to_selectVenueFragment)
  }

  fun handleInputTitleChanged() { updateInputTitleError(null) }
  fun handleInputDescriptionChanged() { updateInputDescriptionError(null) }
  fun handleInputDateChanged() { updateInputDateError(null) }
  fun handleInputStartTimeChanged() { updateInputStartTimeError(null) }
  fun handleInputEndTimeChanged() { updateInputEndTimeError(null) }
  fun handleInputExpectedStrengthChanged() { updateInputExpectedStrength(null) }
  fun handleInputBookingTypeChanged() { updateInputBookingTypeError(null) }
  fun handleInputVenueTypeChanged() { updateVenueTypeError(null) }

  private lateinit var eventStartTime: Calendar
  private lateinit var eventEndTime: Calendar
  private var eventDuration: Int = 0

  private fun validateStartAndEndTime(eventDate: Calendar, startTime: Calendar, endTime: Calendar): Boolean {
    eventStartTime =
      startTime.apply {
        set(Calendar.YEAR, eventDate.get(Calendar.YEAR))
        set(Calendar.MONTH, eventDate.get(Calendar.MONTH))
        set(Calendar.DAY_OF_MONTH, eventDate.get(Calendar.DAY_OF_MONTH))
      }

    eventEndTime =
      endTime.apply {
        set(Calendar.YEAR, eventDate.get(Calendar.YEAR))
        set(Calendar.MONTH, eventDate.get(Calendar.MONTH))
        set(Calendar.DAY_OF_MONTH, eventDate.get(Calendar.DAY_OF_MONTH))
      }

    eventDuration = getDurationInMinute(eventStartTime, eventEndTime)
    if(eventDuration <= 0) {
      updateInputEndTimeError("End Time should be ahead of Start Time")
      return false
    }

    if(eventDuration <=15) {
      updateInputEndTimeError("End Time should be atleast 15 minutes ahead of Start Time")
      return false
    }

    return true
  }
}
