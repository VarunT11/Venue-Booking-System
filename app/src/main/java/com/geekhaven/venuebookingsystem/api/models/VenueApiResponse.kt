package com.geekhaven.venuebookingsystem.api.models

import com.google.gson.annotations.SerializedName

data class VenueResponse(
  @SerializedName("id")
  val id: String? = null,

  @SerializedName("name")
  val name: String,

  @SerializedName("building_id")
  val buildingId: String?,

  @SerializedName("floor_number")
  val floorNumber: Int,

  @SerializedName("venue_type")
  val venueType: String,

  @SerializedName("is_accessible")
  val isAccessible: Boolean,

  @SerializedName("seating_capacity")
  val seatingCapacity: Int,

  @SerializedName("has_air_conditioner")
  val hasAirConditioner: Boolean,

  @SerializedName("has_projectors")
  val hasProjectors: Boolean,

  @SerializedName("has_speakers")
  val hasSpeakers: Boolean,

  @SerializedName("has_whiteboard")
  val hasWhiteboard: Boolean,

  @SerializedName("authority_id")
  val authorityId: String?
)

data class VenueApiResponse(
  @SerializedName("response_data")
  val venueResponse: VenueResponse
)

data class VenueListApiResponse(
  @SerializedName("response_data")
  val venueResponseList: List<VenueResponse>
)
