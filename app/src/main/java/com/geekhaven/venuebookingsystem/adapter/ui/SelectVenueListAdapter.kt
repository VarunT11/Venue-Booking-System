package com.geekhaven.venuebookingsystem.adapter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.adapter.models.items.SelectVenueListItem
import com.geekhaven.venuebookingsystem.adapter.models.items.VenueListItem
import com.geekhaven.venuebookingsystem.databinding.ItemVenueListBinding
import com.geekhaven.venuebookingsystem.databinding.SelectVenueListItemBinding

class SelectVenueListAdapter(
    private val itemList: ArrayList<SelectVenueListItem>,
    onClick: (String) -> Unit
): AbsListAdapter<SelectVenueListItemBinding, SelectVenueListItem>(itemList, onClick) {

    override fun inflateViewBinding(parent: ViewGroup) =
        SelectVenueListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun getListItemId(position: Int): String = itemList[position].id

    override fun renderItem(item: SelectVenueListItem, binding: SelectVenueListItemBinding) {
        binding.selectVenueListItemName.text = item.name
        binding.selectVenueListItemBuilding.text = item.buildingName
        binding.selectVenueListItemVenueType.text = item.venueType
        binding.selectVenueListItemCapacity.text = item.capacity
    }

}
