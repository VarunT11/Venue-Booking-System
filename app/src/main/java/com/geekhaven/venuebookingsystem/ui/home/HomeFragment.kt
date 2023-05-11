package com.geekhaven.venuebookingsystem.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.databinding.FragmentHomeBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class HomeFragment : AbsFragment<FragmentHomeBinding, HomeVM>() {

  override val fragmentName: String = HomeFragment::class.java.name
  override val vmClass: Class<HomeVM> = HomeVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentHomeBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {}

  override fun addViewListeners() {
    binding.btnCreateBooking.setOnClickListener {

    }
  }
}
