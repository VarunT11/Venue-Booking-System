package com.geekhaven.venuebookingsystem.adapter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingListItem
import com.geekhaven.venuebookingsystem.databinding.ItemBookingListBinding

class BookingListAdapter(
  private val bookingList: ArrayList<BookingListItem>,
  onClick: (String) -> Unit,
): AbsListAdapter<ItemBookingListBinding, BookingListItem>(bookingList, onClick) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemBookingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int) = bookingList[position].bookingId

  override fun renderItem(item: BookingListItem, binding: ItemBookingListBinding
  ) {
    binding.tvItemBookingName.text = item.venueName
    binding.tvItemBookingDate.text = item.eventDate
  }

}
