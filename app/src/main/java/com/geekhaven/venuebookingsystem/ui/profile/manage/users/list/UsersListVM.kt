package com.geekhaven.venuebookingsystem.ui.profile.manage.users.list

import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.SearchBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class UsersListVM: AbsFragmentVM() {
  override fun onFragmentStart() {
    mainVM.setAppBarConfig(
      AppBarConfig(
      titleBarConfig = TitleBarConfig(
        showBackButton = true,
        title = "View Users List",
      ),
      searchBarConfig = SearchBarConfig(
        hint = "Search Users",
      )
    )
    )
  }

}
