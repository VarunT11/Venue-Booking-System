package com.geekhaven.venuebookingsystem.adapter.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingHomeRequestListItem
import com.geekhaven.venuebookingsystem.databinding.ItemBookingHomeRequestBinding
import com.geekhaven.venuebookingsystem.models.type.BookingRequestStatus

class BookingHomeRequestListAdapter(
  private val context: Context,
  private val requestList: ArrayList<BookingHomeRequestListItem>,
  onClick: (String) -> Unit,
): AbsListAdapter<ItemBookingHomeRequestBinding, BookingHomeRequestListItem>(requestList, onClick) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemBookingHomeRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int): String = requestList[position].id

  override fun renderItem(item: BookingHomeRequestListItem, binding: ItemBookingHomeRequestBinding) {
    binding.tvBookingHomeRequestItemUser.text = item.userName
    binding.tvBookingHomeRequestItemStatus.text = item.requestStatus.toDisplayString()
    binding.tvBookingHomeRequestItemTime.text = item.lastUpdatedTime

    binding.imgBookingHomeRequestItemStatus.setImageDrawable(ContextCompat.getDrawable(context, getImageDrawable(item.requestStatus)))
  }

  private fun getImageDrawable(status: BookingRequestStatus): Int {
    return when(status) {
      is BookingRequestStatus.Approved -> R.drawable.ic_request_approved
      else -> R.drawable.ic_request_status_pending
    }
  }
}
