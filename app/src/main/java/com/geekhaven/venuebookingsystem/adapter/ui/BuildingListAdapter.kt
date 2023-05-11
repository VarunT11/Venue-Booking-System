package com.geekhaven.venuebookingsystem.adapter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.databinding.ItemBuildingListBinding
import com.geekhaven.venuebookingsystem.api.models.BuildingResponse

class BuildingListAdapter(
  private val buildingResponseList: ArrayList<BuildingResponse>,
  onClick: (buildingId: String?)->Unit,
): AbsListAdapter<ItemBuildingListBinding, BuildingResponse>(buildingResponseList, onClick) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemBuildingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int) = buildingResponseList[position].id!!

  override fun renderItem(item: BuildingResponse, binding: ItemBuildingListBinding) {

  }

}
