package com.geekhaven.venuebookingsystem.ui.bookings

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingHomeRequestListItem
import com.geekhaven.venuebookingsystem.adapter.ui.BookingHomeRequestListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentBookingHomeBinding
import com.geekhaven.venuebookingsystem.models.type.BookingStatus
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.utils.toDateString
import com.geekhaven.venuebookingsystem.utils.toTimeString

class BookingHomeFragment : AbsFragment<FragmentBookingHomeBinding, BookingHomeVM>() {

  override val fragmentName: String = BookingHomeFragment::class.java.simpleName
  override val vmClass: Class<BookingHomeVM> = BookingHomeVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentBookingHomeBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getBookingDetails().observe(viewLifecycleOwner) {
      binding.tvBookingHomeTitle.text = it.title
      binding.tvBookingHomeDescription.text = it.description
      binding.tvBookingHomeDate.text = it.eventStartTime.toDateString()
      binding.tvBookingHomeTime.text = "${it.eventStartTime.toTimeString()} - ${it.eventEndTime.toTimeString()}"
      binding.tvBookingHomeExpectedStrength.text = "Expected Strength: ${it.expectedStrength}"
      binding.tvBookingHomeBookingTime.text = "Booked on ${it.bookingTime?.toTimeString()},${it.bookingTime?.toDateString()}"
      binding.tvBookingHomeLastUpdatedTime.text = "Last updated on ${it.lastUpdatedTime?.toTimeString()},${it.lastUpdatedTime?.toDateString()}"
      binding.tvBookingHomeStatus.text = it.bookingStatus?.toDisplayResponse()
      binding.tvBookingHomeStatus.setTextColor(getTextColor(it.bookingStatus!!))
      binding.mcvBookingHomeStatus.strokeColor = getTextColor(it.bookingStatus)
    }

    mVM.getLocationDetails().observe(viewLifecycleOwner) {
      binding.tvBookingHomeLocation.text = it
    }

    mVM.getRequestDisplayList().observe(viewLifecycleOwner) {
      renderRequestsList(it)
    }
  }

  private fun getTextColor(status: BookingStatus): Int {
    return when(status){
      is BookingStatus.Pending -> Color.parseColor("#5DB4F7")
      is BookingStatus.Approved -> Color.parseColor("#53AB68")
      is BookingStatus.Rejected -> Color.parseColor("#EB575A")
      is BookingStatus.Cancelled -> Color.parseColor("#E6B664")
      is BookingStatus.AutomaticallyDeclined -> Color.parseColor("#313465")
    }
  }

  private fun renderRequestsList(list: List<BookingHomeRequestListItem>){
    LinearLayoutManager(requireContext())
      .apply { orientation = LinearLayoutManager.VERTICAL }
      .let { binding.rcvBookingHomeRequestStatus.layoutManager = it }

    BookingHomeRequestListAdapter(requireContext(), ArrayList(list)) {}
      .let { binding.rcvBookingHomeRequestStatus.adapter = it }
  }

  override fun addViewListeners() {}

}
