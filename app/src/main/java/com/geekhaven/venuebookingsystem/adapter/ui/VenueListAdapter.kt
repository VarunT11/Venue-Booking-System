package com.geekhaven.venuebookingsystem.adapter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.databinding.ItemVenueListBinding
import com.geekhaven.venuebookingsystem.api.models.VenueResponse

class VenueListAdapter(
  private val venueResponseList: ArrayList<VenueResponse>,
  onClick: (String) -> Unit
): AbsListAdapter<ItemVenueListBinding, VenueResponse>(venueResponseList, onClick) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemVenueListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int) = venueResponseList[position].id!!

  override fun renderItem(item: VenueResponse, binding: ItemVenueListBinding) {
    binding.tvVenueListItemName.text = item.name
    binding.tvVenueItemListFloor.text = item.floorNumber.toString()
    binding.tvVenueItemListCapacity.text = item.seatingCapacity.toString()
  }
}
