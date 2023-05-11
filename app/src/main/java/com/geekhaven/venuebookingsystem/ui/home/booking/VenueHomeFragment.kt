package com.geekhaven.venuebookingsystem.ui.home.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.calendar.CalendarAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentVenueHomeBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.utils.toDateString
import java.util.*

class VenueHomeFragment : AbsFragment<FragmentVenueHomeBinding, VenueHomeVM>() {

  override val fragmentName: String = FragmentVenueHomeBinding::class.java.simpleName
  override val vmClass: Class<VenueHomeVM> = VenueHomeVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentVenueHomeBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
  }

  override fun addViewListeners() {
  }

}
