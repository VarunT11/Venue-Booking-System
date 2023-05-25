package com.geekhaven.venuebookingsystem.calendar

import android.graphics.Color
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEntity
import com.geekhaven.venuebookingsystem.api.models.BookingResponse
import com.geekhaven.venuebookingsystem.models.data.Booking
import com.geekhaven.venuebookingsystem.models.type.BookingStatus
import com.geekhaven.venuebookingsystem.utils.addMinuteToCalendarInstant
import com.geekhaven.venuebookingsystem.utils.getCalendarFromInstant
import java.util.*

class CalendarAdapter: WeekView.SimpleAdapter<Booking>() {

  override fun onCreateEntity(item: Booking): WeekViewEntity {
    return WeekViewEntity.Event.Builder(item)
      .setId(item.id.hashCode().toLong())
      .setTitle(item.title)
      .setStartTime(item.eventStartTime)
      .setEndTime(item.eventEndTime)
      .setStyle(WeekViewEntity.Style.Builder().setBackgroundColor(getColorIntFromStatus(item.bookingStatus)).build())
      .build()
  }

  private fun getColorIntFromStatus(status: BookingStatus?): Int {
    if(status == null) {
      return Color.parseColor("#708FBD")
    }

    return when(status) {
      is BookingStatus.Approved -> Color.parseColor("#53AB68")
      is BookingStatus.Pending -> Color.parseColor("#E6B664")
      else -> Color.parseColor("#000000")
    }
  }

}
