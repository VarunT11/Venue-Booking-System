package com.geekhaven.venuebookingsystem.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

fun Long.toDateString(): String = Calendar.getInstance()
  .apply { timeInMillis = this@toDateString }.toDateString()

fun String.getCalendarFromInstant(): Calendar = Calendar.getInstance()
  .apply { time = Date.from(Instant.parse(this@getCalendarFromInstant)) }

fun String.addMinuteToCalendarInstant(duration: Int): Calendar =
  getCalendarFromInstant().apply { add(Calendar.MINUTE, duration) }

fun Calendar.toInstantString() = this.toInstant().toString()

fun Calendar.toDateString(): String = SimpleDateFormat("dd MMMM YYYY").format(this.time)

fun Calendar.toTimeString(): String = SimpleDateFormat("hh:mm a").format(this.time)

fun getDurationInMinute(startTime: Calendar, endTime: Calendar): Int {
  val endMinute = endTime.get(Calendar.HOUR_OF_DAY)*60 + endTime.get(Calendar.MINUTE)
  val startMinute = startTime.get(Calendar.HOUR_OF_DAY)*60 + startTime.get(Calendar.MINUTE)
  return endMinute - startMinute
}
