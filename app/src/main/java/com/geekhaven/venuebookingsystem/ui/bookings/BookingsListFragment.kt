package com.geekhaven.venuebookingsystem.ui.bookings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingListItem
import com.geekhaven.venuebookingsystem.adapter.ui.BookingListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentBookingsListBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class BookingsListFragment : AbsFragment<FragmentBookingsListBinding, BookingsListVM>() {

  override val fragmentName: String = BookingsListFragment::class.java.simpleName
  override val vmClass: Class<BookingsListVM> = BookingsListVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentBookingsListBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getDisplayList().observe(viewLifecycleOwner) { renderBookingList(it) }
  }

  private fun renderBookingList(list: List<BookingListItem>){
    LinearLayoutManager(requireContext())
      .apply { orientation = LinearLayoutManager.VERTICAL }
      .let { binding.rcvBookingsList.layoutManager = it }

    BookingListAdapter(ArrayList(list)) { mVM.handleBookingSelect(it) }
      .let { binding.rcvBookingsList.adapter = it }
  }

  override fun addViewListeners() {}

}
