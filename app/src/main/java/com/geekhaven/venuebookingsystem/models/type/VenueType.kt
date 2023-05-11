package com.geekhaven.venuebookingsystem.models.type

sealed class VenueType {
  object Classroom: VenueType()
  object Lab: VenueType()
  object MeetingRoom: VenueType()
  object Auditorium: VenueType()
  object Other: VenueType()

  fun getStringResponse() = when(this) {
    is Classroom -> "CLASSROOM"
    is Lab -> "LAB"
    is MeetingRoom -> "MEETING_ROOM"
    is Auditorium -> "AUDITORIUM"
    is Other -> "OTHER"
  }
}

fun String.getVenueType() = when (this) {
  "CLASSROOM" -> VenueType.Classroom
  "LAB" -> VenueType.Lab
  "MEETING_ROOM" -> VenueType.MeetingRoom
  "AUDITORIUM" -> VenueType.Auditorium
  "OTHER" -> VenueType.Other
  else -> VenueType.Other
}
