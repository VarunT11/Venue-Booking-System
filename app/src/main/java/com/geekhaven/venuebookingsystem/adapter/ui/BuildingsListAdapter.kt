package com.geekhaven.venuebookingsystem.adapter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.databinding.ItemBuildingListBinding
import com.geekhaven.venuebookingsystem.models.data.Building

class BuildingsListAdapter(
  private val itemsList: ArrayList<Building>,
  onClick: (String) -> Unit,
  private val onEditSelect: (String) -> Unit,
  private val onDeleteSelect: (String) -> Unit,
): AbsListAdapter<ItemBuildingListBinding, Building>(itemsList, onClick) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemBuildingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int): String = itemsList[position].id!!

  override fun renderItem(item: Building, binding: ItemBuildingListBinding) {
    binding.buildingListItemName.text = item.name
    binding.btnBuildingListItemEdit.setOnClickListener { onEditSelect(item.id!!) }
    binding.btnBuildingListItemRemove.setOnClickListener { onDeleteSelect(item.id!!) }
  }

}
