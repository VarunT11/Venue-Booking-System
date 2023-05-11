package com.geekhaven.venuebookingsystem.adapter.models.type

sealed class ManageOptionType{
  object Add: ManageOptionType()
  object View: ManageOptionType()
}
