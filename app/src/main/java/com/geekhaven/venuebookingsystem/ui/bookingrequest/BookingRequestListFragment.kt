package com.geekhaven.venuebookingsystem.ui.bookingrequest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.models.items.BookingRequestListItem
import com.geekhaven.venuebookingsystem.adapter.ui.BookingRequestListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentBookingRequestListBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class BookingRequestListFragment : AbsFragment<FragmentBookingRequestListBinding, BookingRequestListVM>() {

  override val fragmentName: String = BookingRequestListFragment::class.java.simpleName
  override val vmClass: Class<BookingRequestListVM> = BookingRequestListVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentBookingRequestListBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getDisplayList().observe(viewLifecycleOwner) { renderBookingRequestsList(it) }
  }

  private fun renderBookingRequestsList(list: List<BookingRequestListItem>){
    LinearLayoutManager(requireContext())
      .apply { orientation = LinearLayoutManager.VERTICAL }
      .let { binding.rcvBookingRequestList.layoutManager = it }

    BookingRequestListAdapter(ArrayList(list)) { mVM.handleBookingRequestSelect(it) }
      .let { binding.rcvBookingRequestList.adapter = it }
  }

  override fun addViewListeners() {}

}
