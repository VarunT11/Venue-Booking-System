package com.geekhaven.venuebookingsystem.adapter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.adapter.models.items.VenueListItem
import com.geekhaven.venuebookingsystem.databinding.ItemVenueListBinding

class VenueListAdapter(
  private val venueResponseList: ArrayList<VenueListItem>,
  onClick: (String) -> Unit,
  private val onEditSelect: (String) -> Unit,
  private val onRemoveSelect: (String) -> Unit,
): AbsListAdapter<ItemVenueListBinding, VenueListItem>(venueResponseList, onClick) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemVenueListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int) = venueResponseList[position].id

  override fun renderItem(item: VenueListItem, binding: ItemVenueListBinding) {
    binding.venueListItemName.text = item.name
    binding.venueListItemBuilding.text = item.buildingName

    binding.btnVenueListItemEdit.setOnClickListener { onEditSelect(item.id) }
    binding.btnVenueListItemRemove.setOnClickListener { onRemoveSelect(item.id) }
  }
}
