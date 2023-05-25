package com.geekhaven.venuebookingsystem.adapter.models.items

import com.geekhaven.venuebookingsystem.models.type.BookingStatus

data class BookingListItem(
  val id: String,
  val title: String,
  val bookingType: String,
  val location: String,
  val eventDate: String,
  val bookingStatus: BookingStatus,
)
