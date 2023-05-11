package com.geekhaven.venuebookingsystem.api.models

import com.google.gson.annotations.SerializedName

data class BookingResponse(
  @SerializedName("id")
  val id: String? = null,

  @SerializedName("user_id")
  val userId: String,

  @SerializedName("venue_id")
  val venueId: String,

  @SerializedName("booking_time")
  val bookingTime: String? = null,

  @SerializedName("event_time")
  val eventTime: String,

  @SerializedName("last_updated_time")
  val lastUpdatedTime: String? = null,

  @SerializedName("booking_status")
  val bookingStatus: String? = null,

  @SerializedName("booking_type")
  val bookingType: String,

  @SerializedName("event_duration")
  val eventDuration: Int,

  @SerializedName("expected_strength")
  val expectedStrength: Int,

  @SerializedName("title")
  val title: String,

  @SerializedName("description")
  val description: String
)

data class BookingRequestResponse(
  @SerializedName("id")
  val id: String,

  @SerializedName("booking_id")
  val bookingId: String,

  @SerializedName("receiver_id")
  val receiverId: String,

  @SerializedName("request_status")
  val requestStatus: String,

  @SerializedName("last_updated_time")
  val lastUpdatedTime: String
)

data class BookingApiResponse(
  @SerializedName("response_data")
  val bookingResponse: BookingResponse
)

data class BookingListApiResponse(
  @SerializedName("response_data")
  val bookingResponseList: List<BookingResponse>
)

data class BookingRequestApiResponse(
  @SerializedName("response_data")
  val bookingRequestResponse: BookingRequestResponse
)

data class BookingRequestListApiResponse(
  @SerializedName("response_data")
  val bookingRequestResponseList: List<BookingRequestResponse>
)
