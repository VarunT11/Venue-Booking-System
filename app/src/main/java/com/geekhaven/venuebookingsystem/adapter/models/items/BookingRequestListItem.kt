package com.geekhaven.venuebookingsystem.adapter.models.items

data class BookingRequestListItem(
  val requestId: String,
  val venueName: String,
  val date: Long,
  val userName: String,
  val bookingStatus: String,
)
