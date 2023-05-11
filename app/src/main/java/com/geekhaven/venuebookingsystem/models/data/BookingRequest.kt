package com.geekhaven.venuebookingsystem.models.data

import com.geekhaven.venuebookingsystem.api.models.BookingRequestResponse
import com.geekhaven.venuebookingsystem.models.type.BookingRequestStatus
import com.geekhaven.venuebookingsystem.models.type.getBookingRequestStatus
import com.geekhaven.venuebookingsystem.utils.getCalendarFromInstant
import com.geekhaven.venuebookingsystem.utils.toInstantString
import java.util.Calendar

data class BookingRequest(
  val id: String,
  val bookingId: String,
  val receiverId: String,
  val requestStatus: BookingRequestStatus,
  val lastUpdatedTime: Calendar
)

fun BookingRequestResponse.toBookingRequest() = BookingRequest(
  id = id,
  bookingId = bookingId,
  receiverId = receiverId,
  requestStatus = requestStatus.getBookingRequestStatus(),
  lastUpdatedTime = lastUpdatedTime.getCalendarFromInstant()
)

fun BookingRequest.toBookingRequestResponse() = BookingRequestResponse(
  id = id,
  bookingId = bookingId,
  receiverId = receiverId,
  requestStatus = requestStatus.toStringResponse(),
  lastUpdatedTime = lastUpdatedTime.toInstantString()
)
