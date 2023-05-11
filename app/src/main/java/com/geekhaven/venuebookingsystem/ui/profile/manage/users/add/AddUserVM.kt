package com.geekhaven.venuebookingsystem.ui.profile.manage.users.add

import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.config.ui.TitleBarConfig
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class AddUserVM: AbsFragmentVM() {

  override fun onFragmentStart() {
    mainVM.setAppBarConfig(AppBarConfig(
      titleBarConfig = TitleBarConfig(
        showBackButton = true,
        title = "Add a new User",
      )
    ))
  }

}
