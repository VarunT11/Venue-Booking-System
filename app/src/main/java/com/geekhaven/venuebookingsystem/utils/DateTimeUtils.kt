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

fun Calendar.toDateString(): String = SimpleDateFormat("E dd-MM-YYYY").format(this.time)

fun Calendar.toTimeString(): String = SimpleDateFormat("hh:mm").format(this.time)
