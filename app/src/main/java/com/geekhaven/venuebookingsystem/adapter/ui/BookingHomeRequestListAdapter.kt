package com.geekhaven.venuebookingsystem.adapter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingHomeRequestListItem
import com.geekhaven.venuebookingsystem.databinding.ItemBookingHomeRequestBinding

class BookingHomeRequestListAdapter(
  private val requestList: ArrayList<BookingHomeRequestListItem>,
  onClick: (String) -> Unit,
): AbsListAdapter<ItemBookingHomeRequestBinding, BookingHomeRequestListItem>(requestList, onClick) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemBookingHomeRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int): String = requestList[position].requestId

  override fun renderItem(item: BookingHomeRequestListItem, binding: ItemBookingHomeRequestBinding) {
    binding.tvItemBookingHomeRequestUserName.text = item.userName
    binding.tvItemBookingHomeRequestStatus.text = item.requestStatus
  }
}
