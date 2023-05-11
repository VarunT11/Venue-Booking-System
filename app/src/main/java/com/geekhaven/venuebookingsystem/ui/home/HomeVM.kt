package com.geekhaven.venuebookingsystem.ui.home

import com.geekhaven.venuebookingsystem.config.ui.AppBarConfig
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragmentVM

class HomeVM: AbsFragmentVM() {

  override fun onFragmentStart() {
    mainVM.setAppBarConfig(AppBarConfig())
  }

}
