package com.geekhaven.venuebookingsystem.models.data

import com.geekhaven.venuebookingsystem.api.models.VenueResponse
import com.geekhaven.venuebookingsystem.models.type.VenueType
import com.geekhaven.venuebookingsystem.models.type.getVenueType

data class Venue(
  val id: String?,
  val name: String,
  val buildingId: String?,
  val floorNumber: Int,
  val venueType: VenueType,
  val isAccessible: Boolean,
  val seatingCapacity: Int,
  val hasAirConditioner: Boolean,
  val hasProjectors: Boolean,
  val hasSpeakers: Boolean,
  val hasWhiteboard: Boolean,
  val authorityId: String?,
)

fun VenueResponse.toVenue() = Venue(
  id = id,
  name = name,
  buildingId = buildingId,
  floorNumber = floorNumber,
  venueType = venueType.getVenueType(),
  isAccessible = isAccessible,
  seatingCapacity = seatingCapacity,
  hasAirConditioner = hasAirConditioner,
  hasProjectors = hasProjectors,
  hasSpeakers = hasSpeakers,
  hasWhiteboard = hasWhiteboard,
  authorityId = authorityId,
)

fun Venue.toVenueResponse() = VenueResponse(
  id = id,
  name = name,
  buildingId = buildingId,
  floorNumber = floorNumber,
  venueType = venueType.getStringResponse(),
  isAccessible = isAccessible,
  seatingCapacity = seatingCapacity,
  hasAirConditioner = hasAirConditioner,
  hasProjectors = hasProjectors,
  hasSpeakers = hasSpeakers,
  hasWhiteboard = hasWhiteboard,
  authorityId = authorityId,
)
