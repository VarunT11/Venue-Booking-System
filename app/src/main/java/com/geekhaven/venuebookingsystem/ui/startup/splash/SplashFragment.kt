package com.geekhaven.venuebookingsystem.ui.startup.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.databinding.FragmentSplashBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class SplashFragment : AbsFragment<FragmentSplashBinding, SplashVM>() {

  override val fragmentName: String = SplashFragment::class.java.simpleName
  override val vmClass = SplashVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentSplashBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {}
  override fun addViewListeners() {}

}
