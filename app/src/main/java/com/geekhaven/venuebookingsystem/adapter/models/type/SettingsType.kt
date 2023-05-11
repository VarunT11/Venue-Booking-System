package com.geekhaven.venuebookingsystem.adapter.models.type

sealed class SettingsType{
  object EditProfile: SettingsType()
  object ManageUsers: SettingsType()
  object ManageBuildings: SettingsType()
  object ManageVenues: SettingsType()
  object About: SettingsType()
  object Logout: SettingsType()
}
