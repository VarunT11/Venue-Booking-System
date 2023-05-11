package com.geekhaven.venuebookingsystem.ui.profile.manage.venues.home

import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.SearchBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class ManageVenuesHomeVM: AbsFragmentVM() {

  override fun onFragmentStart() {
    mainVM.setAppBarConfig(AppBarConfig(
      titleBarConfig = TitleBarConfig(
        title = "Manage Venues",
        showBackButton = false
      ),
      searchBarConfig = SearchBarConfig(
        hint = "Search Buildings"
      )
    ))
  }

}
