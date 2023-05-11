package com.geekhaven.venuebookingsystem.adapter.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.adapter.models.items.ManageOptionItem
import com.geekhaven.venuebookingsystem.adapter.models.type.ManageOptionType
import com.geekhaven.venuebookingsystem.databinding.ItemManageOptionBinding

class ManageOptionAdapter(
  private val context: Context,
  private val optionsList: ArrayList<ManageOptionItem>,
  private val onOptionClick: (ManageOptionType) -> Unit
): AbsListAdapter<ItemManageOptionBinding, ManageOptionItem>(optionsList, {  }) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemManageOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int): String = ""

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    renderItem(optionsList[position], holder.binding)
    holder.itemView.setOnClickListener { onOptionClick(optionsList[position].optionType) }
  }

  override fun renderItem(item: ManageOptionItem, binding: ItemManageOptionBinding) {
    binding.tvItemProfileLabel.text = item.label
    binding.imgItemProfileIcon.setImageDrawable(getDrawableFromType(item.optionType))
  }

  private fun getDrawableFromType(optionType: ManageOptionType): Drawable? {
    val resId = when(optionType){
      is ManageOptionType.Add -> R.drawable.ic_add
      is ManageOptionType.View -> R.drawable.ic_view_list
    }
    return getDrawableInt(resId)
  }

  private fun getDrawableInt(id: Int) =
    AppCompatResources.getDrawable(context, id)

}
