package com.geekhaven.venuebookingsystem.adapter.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.databinding.ItemProfileSettingsBinding
import com.geekhaven.venuebookingsystem.adapter.models.items.ProfileSettingsItem
import com.geekhaven.venuebookingsystem.adapter.models.type.SettingsType

class ProfileSettingsAdapter(
  private val context: Context,
  private val settingsList: ArrayList<ProfileSettingsItem>,
  private val onItemClick: (SettingsType) -> Unit
): AbsListAdapter<ItemProfileSettingsBinding, ProfileSettingsItem>(settingsList, {  }) {

  override fun inflateViewBinding(parent: ViewGroup) =
    ItemProfileSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

  override fun getListItemId(position: Int): String = ""

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    renderItem(settingsList[position], holder.binding)
    holder.itemView.setOnClickListener { onItemClick(settingsList[position].type) }
  }

  override fun renderItem(item: ProfileSettingsItem, binding: ItemProfileSettingsBinding) {
    binding.tvItemProfileLabel.text = item.name
    binding.imgItemProfileIcon.setImageDrawable(getDrawableFromType(item.type))
  }

  private fun getDrawableFromType(settingsType: SettingsType): Drawable? {
    val resId = when(settingsType){
      is SettingsType.EditProfile -> R.drawable.ic_edit
      is SettingsType.ManageUsers -> R.drawable.ic_manage_users
      is SettingsType.ManageBuildings -> R.drawable.ic_manage_buildings
      is SettingsType.ManageVenues -> R.drawable.ic_manage_venues
      is SettingsType.About -> R.drawable.ic_info
      is SettingsType.Logout -> R.drawable.ic_logout
    }
    return getDrawableInt(resId)
  }

  private fun getDrawableInt(id: Int) =
    AppCompatResources.getDrawable(context, id)
}
