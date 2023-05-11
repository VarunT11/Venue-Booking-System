package com.geekhaven.venuebookingsystem.config.ui

data class AppBarConfig(
  val titleBarConfig: TitleBarConfig? = null,
  val searchBarConfig: SearchBarConfig? = null,
)

data class TitleBarConfig(
  val showBackButton: Boolean = false,
  val title: String,
  val titleIconRes: Int? = null,
)

data class SearchBarConfig(
  val hint: String,
  val showFilterButton: Boolean = true,
)
