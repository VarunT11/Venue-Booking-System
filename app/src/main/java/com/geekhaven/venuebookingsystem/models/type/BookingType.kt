package com.geekhaven.venuebookingsystem.models.type

sealed class BookingType {
  object Academic: BookingType()
  object Workshop: BookingType()
  object Event: BookingType()
  object Other: BookingType()

  fun getStringResponse() = when(this) {
    is BookingType.Academic -> "ACADEMIC"
    is BookingType.Workshop -> "WORKSHOP"
    is BookingType.Event -> "EVENT"
    is BookingType.Other -> "OTHER"
  }

  fun toDisplayString() = when(this) {
    is BookingType.Academic -> "Academic"
    is BookingType.Workshop -> "Workshop"
    is BookingType.Event -> "Event"
    is BookingType.Other -> "Other"
  }
}

fun String.getBookingType() = when(this) {
  "ACADEMIC" -> BookingType.Academic
  "WORKSHOP" -> BookingType.Workshop
  "EVENT" -> BookingType.Event
  "OTHER" -> BookingType.Other
  else -> BookingType.Other
}

