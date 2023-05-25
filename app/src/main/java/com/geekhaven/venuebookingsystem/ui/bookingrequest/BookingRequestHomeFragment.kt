package com.geekhaven.venuebookingsystem.ui.bookingrequest

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingHomeRequestListItem
import com.geekhaven.venuebookingsystem.adapter.ui.BookingHomeRequestListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentBookingRequestHomeBinding
import com.geekhaven.venuebookingsystem.models.type.BookingRequestStatus
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.utils.toDateString
import com.geekhaven.venuebookingsystem.utils.toTimeString

class BookingRequestHomeFragment : AbsFragment<FragmentBookingRequestHomeBinding, BookingRequestHomeVM>() {

  override val fragmentName: String = BookingRequestHomeFragment::class.java.simpleName
  override val vmClass: Class<BookingRequestHomeVM> = BookingRequestHomeVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentBookingRequestHomeBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getBookingDetails().observe(viewLifecycleOwner) {
      binding.tvBookingRequestHomeTitle.text = it.title
      binding.tvBookingRequestHomeType.text = it.bookingType.toDisplayString()
      binding.tvBookingRequestHomeDate.text = it.eventStartTime.toDateString()
      binding.tvBookingRequestHomeTime.text = "${it.eventStartTime.toTimeString()} - ${it.eventEndTime.toTimeString()}"
    }

    mVM.getLocationDetails().observe(viewLifecycleOwner) {
      binding.tvBookingRequestHomeLocation.text = it
    }

    mVM.getRequestDisplayList().observe(viewLifecycleOwner) { renderRequestsList(it) }

    mVM.getBookingRequestStatus().observe(viewLifecycleOwner) {
      binding.tvBookingRequestHomeStatus.text = it.toDisplayString()
      binding.tvBookingRequestHomeStatus.setTextColor(getTextColor(it))
      binding.mcvBookingRequestHomeStatus.strokeColor = getTextColor(it)

      when(it){
        is BookingRequestStatus.Received -> binding.layoutBookingRequestHomeAction.visibility = View.VISIBLE
        else -> binding.layoutBookingRequestHomeAction.visibility = View.GONE
      }
    }
  }

  override fun addViewListeners() {
    binding.btnBookingRequestHomeApprove.setOnClickListener { mVM.approveBookingRequest() }
    binding.btnBookingRequestHomeReject.setOnClickListener { mVM.rejectBookingRequest() }
  }

  private fun getTextColor(status: BookingRequestStatus): Int {
    return when(status){
      is BookingRequestStatus.PendingReceive -> Color.parseColor("#5DB4F7")
      is BookingRequestStatus.Received -> Color.parseColor("#5DB4F7")
      is BookingRequestStatus.Approved -> Color.parseColor("#53AB68")
      is BookingRequestStatus.Rejected -> Color.parseColor("#EB575A")
      is BookingRequestStatus.Cancelled -> Color.parseColor("#E6B664")
      is BookingRequestStatus.AutomaticallyDeclined -> Color.parseColor("#313465")
    }
  }

  private fun renderRequestsList(list: List<BookingHomeRequestListItem>){
    LinearLayoutManager(requireContext())
      .apply { orientation = LinearLayoutManager.VERTICAL }
      .let { binding.rcvBookingRequestHomeRequestStatus.layoutManager = it }

    BookingHomeRequestListAdapter(requireContext(), ArrayList(list)) {}
      .let { binding.rcvBookingRequestHomeRequestStatus.adapter = it }
  }

}
