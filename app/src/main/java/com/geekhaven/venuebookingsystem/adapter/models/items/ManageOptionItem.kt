package com.geekhaven.venuebookingsystem.adapter.models.items

import com.geekhaven.venuebookingsystem.adapter.models.type.ManageOptionType

data class ManageOptionItem(
  val optionType: ManageOptionType,
  val label: String
)
