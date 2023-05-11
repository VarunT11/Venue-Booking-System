package com.geekhaven.venuebookingsystem.adapter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.databinding.ItemSearchViewBinding
import com.geekhaven.venuebookingsystem.adapter.models.items.SearchItem

class SearchListAdapter(
  private val itemList: ArrayList<SearchItem>,
  onClick: (String) -> Unit,
): AbsListAdapter<ItemSearchViewBinding, SearchItem>(itemList, onClick) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemSearchViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int): String = itemList[position].id

  override fun renderItem(item: SearchItem, binding: ItemSearchViewBinding) {
    binding.tvItemSearchName.text = item.name
  }

}
