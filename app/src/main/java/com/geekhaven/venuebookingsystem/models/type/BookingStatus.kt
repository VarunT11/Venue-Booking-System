package com.geekhaven.venuebookingsystem.models.type

import com.geekhaven.venuebookingsystem.exceptions.SimpleApiException

sealed class BookingStatus{
  object Pending: BookingStatus()
  object Approved: BookingStatus()
  object Rejected: BookingStatus()
  object Cancelled: BookingStatus()
  object AutomaticallyDeclined: BookingStatus()

  fun getStringResponse() = when(this) {
    is Pending -> "PENDING"
    is Approved -> "APPROVED"
    is Rejected -> "REJECTED"
    is Cancelled -> "CANCELLED"
    is AutomaticallyDeclined -> "AUTOMATICALLY_DECLINED"
  }
}

fun String.getBookingStatus() = when(this){
  "PENDING" -> BookingStatus.Pending
  "APPROVED" -> BookingStatus.Approved
  "REJECTED" -> BookingStatus.Rejected
  "CANCELLED" -> BookingStatus.Cancelled
  "AUTOMATICALLY_DECLINED" -> BookingStatus.AutomaticallyDeclined
  else -> throw SimpleApiException("Invalid Booking Status")
}
