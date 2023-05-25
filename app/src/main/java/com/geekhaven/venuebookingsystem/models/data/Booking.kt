package com.geekhaven.venuebookingsystem.models.data

import com.geekhaven.venuebookingsystem.api.models.BookingResponse
import com.geekhaven.venuebookingsystem.models.type.BookingStatus
import com.geekhaven.venuebookingsystem.models.type.BookingType
import com.geekhaven.venuebookingsystem.models.type.getBookingStatus
import com.geekhaven.venuebookingsystem.models.type.getBookingType
import com.geekhaven.venuebookingsystem.utils.addMinuteToCalendarInstant
import com.geekhaven.venuebookingsystem.utils.getCalendarFromInstant
import com.geekhaven.venuebookingsystem.utils.toInstantString
import java.util.Calendar
import java.util.Collections.replaceAll

data class Booking(
  val id: String?,
  val userId: String,
  val venueId: String?,
  val bookingTime: Calendar?,
  val eventStartTime: Calendar,
  val lastUpdatedTime: Calendar?,
  val bookingStatus: BookingStatus?,
  val bookingType: BookingType,
  val eventDuration: Int,
  val eventEndTime: Calendar,
  val expectedStrength: Int,
  val title: String,
  val description: String,
)

fun BookingResponse.toBooking() = Booking(
  id = id,
  userId = userId,
  venueId = venueId,
  bookingTime = bookingTime?.getCalendarFromInstant(),
  eventStartTime = eventTime.getCalendarFromInstant(),
  lastUpdatedTime = lastUpdatedTime?.getCalendarFromInstant(),
  bookingStatus = bookingStatus?.getBookingStatus(),
  bookingType = bookingType.getBookingType(),
  eventDuration = eventDuration,
  eventEndTime = eventTime.addMinuteToCalendarInstant(eventDuration),
  expectedStrength = expectedStrength,
  title = title,
  description = description
)

fun Booking.toBookingResponse() = BookingResponse(
  id = id,
  userId = userId,
  venueId = venueId ?: "",
  bookingTime = bookingTime?.toInstantString(),
  eventTime = eventStartTime.toInstantString(),
  lastUpdatedTime = lastUpdatedTime?.toInstantString(),
  bookingStatus = bookingStatus?.getStringResponse(),
  bookingType = bookingType.getStringResponse(),
  eventDuration = eventDuration,
  expectedStrength = expectedStrength,
  title = title,
  description = description
)
