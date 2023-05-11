package com.geekhaven.venuebookingsystem.calendar

import android.graphics.Color
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEntity
import com.geekhaven.venuebookingsystem.api.models.BookingResponse
import com.geekhaven.venuebookingsystem.utils.addMinuteToCalendarInstant
import com.geekhaven.venuebookingsystem.utils.getCalendarFromInstant
import java.util.*

class CalendarAdapter(
  val emptyTimeClick: (Calendar) -> Unit
): WeekView.SimpleAdapter<BookingResponse>() {

  override fun onCreateEntity(item: BookingResponse): WeekViewEntity {
    return WeekViewEntity.Event.Builder(item)
      .setId(item.id.hashCode().toLong())
      .setTitle("Hello")
      .setStartTime(item.eventTime.getCalendarFromInstant())
      .setEndTime(item.eventTime.addMinuteToCalendarInstant(item.eventDuration))
      .setStyle(WeekViewEntity.Style.Builder().setBackgroundColor(Color.BLUE).build())
      .build()
  }

  override fun onEmptyViewClick(time: Calendar) {
    super.onEmptyViewClick(time)
    emptyTimeClick(time)
  }

  override fun onEventClick(data: BookingResponse) {
    super.onEventClick(data)
    println("Event clicked: $data")
  }
}
