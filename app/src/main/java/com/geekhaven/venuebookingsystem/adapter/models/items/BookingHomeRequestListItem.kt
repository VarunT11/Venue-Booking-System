package com.geekhaven.venuebookingsystem.adapter.models.items

import com.geekhaven.venuebookingsystem.models.type.BookingRequestStatus

data class BookingHomeRequestListItem(
  val id: String,
  val userName: String,
  val requestStatus: BookingRequestStatus,
  val lastUpdatedTime: String,
)
