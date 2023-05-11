package com.geekhaven.venuebookingsystem.ui.bookingrequest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.ui.BookingRequestListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentBookingRequestListBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.ui.home.booking.BuildingListFragment

class BookingRequestListFragment : AbsFragment<FragmentBookingRequestListBinding, BookingRequestListVM>() {

  override val fragmentName: String = BuildingListFragment::class.java.simpleName
  override val vmClass: Class<BookingRequestListVM> = BookingRequestListVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentBookingRequestListBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {

  }

  override fun addViewListeners() {}

}
