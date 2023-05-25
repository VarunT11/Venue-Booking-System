package com.geekhaven.venuebookingsystem.models.type

import com.geekhaven.venuebookingsystem.exceptions.SimpleApiException

sealed class BookingRequestStatus {
  object PendingReceive: BookingRequestStatus()
  object Received: BookingRequestStatus()
  object Rejected: BookingRequestStatus()
  object Approved: BookingRequestStatus()
  object Cancelled: BookingRequestStatus()
  object AutomaticallyDeclined: BookingRequestStatus()

  fun toStringResponse() = when(this) {
    is PendingReceive -> "PENDING_RECEIVE"
    is Received -> "RECEIVED"
    is Rejected -> "REJECTED"
    is Approved -> "APPROVED"
    is Cancelled -> "CANCELLED"
    is AutomaticallyDeclined -> "AUTOMATICALLY_DECLINED"
  }

  fun toDisplayString() = when(this) {
    is PendingReceive -> "Receive Pending"
    is Received -> "Received"
    is Rejected -> "Rejected"
    is Approved -> "Approved"
    is Cancelled -> "Cancelled"
    is AutomaticallyDeclined -> "Automatically Declined"
  }
}

fun String.getBookingRequestStatus() = when(this) {
  "PENDING_RECEIVE" -> BookingRequestStatus.PendingReceive
  "RECEIVED" -> BookingRequestStatus.Received
  "REJECTED" -> BookingRequestStatus.Rejected
  "APPROVED" -> BookingRequestStatus.Approved
  "CANCELLED" -> BookingRequestStatus.Cancelled
  "AUTOMATICALLY_DECLINED" -> BookingRequestStatus.AutomaticallyDeclined
  else -> throw SimpleApiException("Invalid Booking Request Status")
}
