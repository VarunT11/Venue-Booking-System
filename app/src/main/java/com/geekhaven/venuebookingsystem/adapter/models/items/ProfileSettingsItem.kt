package com.geekhaven.venuebookingsystem.adapter.models.items

import com.geekhaven.venuebookingsystem.adapter.models.type.SettingsType

data class ProfileSettingsItem(
  val type: SettingsType,
  val name: String,
)
