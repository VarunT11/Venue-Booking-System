package com.geekhaven.venuebookingsystem.ui.bookings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.ui.BookingHomeRequestListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentBookingHomeBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.ui.home.booking.BuildingListFragment

class BookingHomeFragment : AbsFragment<FragmentBookingHomeBinding, BookingHomeVM>() {

  override val fragmentName: String = BuildingListFragment::class.java.simpleName
  override val vmClass: Class<BookingHomeVM> = BookingHomeVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentBookingHomeBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
  }

  override fun addViewListeners() {}

}
