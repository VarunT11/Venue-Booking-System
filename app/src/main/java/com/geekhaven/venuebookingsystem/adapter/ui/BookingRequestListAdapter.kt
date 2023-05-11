package com.geekhaven.venuebookingsystem.adapter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingRequestListItem
import com.geekhaven.venuebookingsystem.databinding.ItemBookingRequestsListBinding

class BookingRequestListAdapter(
  private val requestsList: ArrayList<BookingRequestListItem>,
  onClick: (id: String) -> Unit,
): AbsListAdapter<ItemBookingRequestsListBinding, BookingRequestListItem>(requestsList, onClick) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemBookingRequestsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int): String = requestsList[position].requestId

  override fun renderItem(item: BookingRequestListItem, binding: ItemBookingRequestsListBinding) {
    binding.tvItemBookingRequestsVenue.text = item.venueName
    binding.tvItemBookingRequestsDate.text = item.date.toString()
    binding.tvItemBookingRequestsUser.text = item.userName
    binding.tvItemBookingRequestsStatus.text = item.bookingStatus
  }
}
