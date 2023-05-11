package com.geekhaven.venuebookingsystem.models.data

import com.geekhaven.venuebookingsystem.api.models.CommentResponse
import com.geekhaven.venuebookingsystem.utils.getCalendarFromInstant
import com.geekhaven.venuebookingsystem.utils.toInstantString
import java.util.Calendar

data class Comment(
  val id: String?,
  val commentTime: Calendar?,
  val userId: String,
  val bookingId: String,
  val content: String,
)

fun CommentResponse.toComment() = Comment(
  id = id,
  commentTime = commentTime?.getCalendarFromInstant(),
  userId = userId,
  bookingId = bookingId,
  content = content,
)

fun Comment.toCommentResponse() = CommentResponse(
  id = id,
  commentTime = commentTime?.toInstantString(),
  userId = userId,
  bookingId = bookingId,
  content = content
)
