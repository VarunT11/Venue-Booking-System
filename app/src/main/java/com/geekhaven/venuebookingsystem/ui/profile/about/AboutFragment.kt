package com.geekhaven.venuebookingsystem.ui.profile.about

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.databinding.FragmentAboutBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class AboutFragment : AbsFragment<FragmentAboutBinding, AboutVM>() {

  override val fragmentName: String = AboutFragment::class.java.simpleName
  override val vmClass: Class<AboutVM> = AboutVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentAboutBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {

  }

  override fun addViewListeners() {

  }

}
