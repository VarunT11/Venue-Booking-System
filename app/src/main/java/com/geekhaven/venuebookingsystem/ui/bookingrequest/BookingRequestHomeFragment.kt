package com.geekhaven.venuebookingsystem.ui.bookingrequest

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.databinding.FragmentBookingRequestHomeBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.ui.home.booking.BuildingListFragment

class BookingRequestHomeFragment : AbsFragment<FragmentBookingRequestHomeBinding, BookingRequestHomeVM>() {

  override val fragmentName: String = BuildingListFragment::class.java.simpleName
  override val vmClass: Class<BookingRequestHomeVM> = BookingRequestHomeVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentBookingRequestHomeBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {}

  override fun addViewListeners() {}

}
