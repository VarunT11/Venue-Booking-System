package com.geekhaven.venuebookingsystem.adapter.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingRequestListItem
import com.geekhaven.venuebookingsystem.databinding.ItemBookingRequestsListBinding
import com.geekhaven.venuebookingsystem.models.type.BookingRequestStatus
import com.geekhaven.venuebookingsystem.models.type.BookingStatus

class BookingRequestListAdapter(
  private val requestsList: ArrayList<BookingRequestListItem>,
  onClick: (id: String) -> Unit,
): AbsListAdapter<ItemBookingRequestsListBinding, BookingRequestListItem>(requestsList, onClick) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemBookingRequestsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int): String = requestsList[position].id

  override fun renderItem(item: BookingRequestListItem, binding: ItemBookingRequestsListBinding) {
    binding.tvBookingRequestListItemTitle.text = item.title
    binding.tvBookingRequestListItemTitle.text = item.title
    binding.tvBookingRequestListItemType.text = item.bookingType
    binding.tvBookingRequestListItemLocation.text = item.location
    binding.tvBookingRequestListItemTime.text = item.eventDate
    binding.tvBookingRequestListItemUser.text = "By ${item.userName}"
    binding.tvBookingRequestListItemStatus.text = item.requestStatus.toDisplayString()
    binding.tvBookingRequestListItemStatus.setTextColor(getTextColor(item.requestStatus))
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

}
