package com.geekhaven.venuebookingsystem.api.models

import com.google.gson.annotations.SerializedName

data class CommentResponse(
  @SerializedName("id")
  val id: String? = null,

  @SerializedName("comment_time")
  val commentTime: String? = null,

  @SerializedName("user_id")
  val userId: String,

  @SerializedName("booking_id")
  val bookingId: String,

  @SerializedName("comment_content")
  val content: String,
)

data class CommentApiResponse(
  @SerializedName("response_data")
  val commentResponse: CommentResponse
)

data class CommentListApiResponse(
  @SerializedName("response_data")
  val commentsList: List<CommentResponse>
)
