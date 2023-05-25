package com.geekhaven.venuebookingsystem.adapter.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingListItem
import com.geekhaven.venuebookingsystem.databinding.ItemBookingListBinding
import com.geekhaven.venuebookingsystem.models.type.BookingStatus

class BookingListAdapter(
  private val bookingList: ArrayList<BookingListItem>,
  onClick: (String) -> Unit,
): AbsListAdapter<ItemBookingListBinding, BookingListItem>(bookingList, onClick) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemBookingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int) = bookingList[position].id

  override fun renderItem(item: BookingListItem, binding: ItemBookingListBinding) {
    binding.tvBookingListItemTitle.text = item.title
    binding.tvBookingListItemType.text = item.bookingType
    binding.tvBookingListItemLocation.text = item.location
    binding.tvBookingListItemTime.text = item.eventDate
    binding.tvBookingListItemStatus.text = item.bookingStatus.toDisplayResponse()
    binding.tvBookingListItemStatus.setTextColor(getTextColor(item.bookingStatus))
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

}
