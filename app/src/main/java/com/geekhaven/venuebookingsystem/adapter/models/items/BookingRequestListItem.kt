package com.geekhaven.venuebookingsystem.adapter.models.items

import com.geekhaven.venuebookingsystem.models.type.BookingRequestStatus

data class BookingRequestListItem(
  val id: String,
  val title: String,
  val bookingType: String,
  val location: String,
  val eventDate: String,
  val requestStatus: BookingRequestStatus,
  val userName: String
)
