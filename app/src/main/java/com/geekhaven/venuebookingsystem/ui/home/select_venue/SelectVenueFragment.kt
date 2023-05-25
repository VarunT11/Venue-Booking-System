package com.geekhaven.venuebookingsystem.ui.home.select_venue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.models.items.SelectVenueListItem
import com.geekhaven.venuebookingsystem.adapter.ui.SelectVenueListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentSelectVenueBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class SelectVenueFragment : AbsFragment<FragmentSelectVenueBinding, SelectVenueVM>() {

    override val fragmentName: String = SelectVenueFragment::class.java.simpleName
    override val vmClass: Class<SelectVenueVM> = SelectVenueVM::class.java

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSelectVenueBinding.inflate(inflater, container, false)

    override fun addLiveDataObservers() {
        mVM.getDisplayList().observe(viewLifecycleOwner) { renderVenueList(it) }
    }

    private fun renderVenueList(venueList: List<SelectVenueListItem>) {
        LinearLayoutManager(requireContext())
            .apply { orientation = LinearLayoutManager.VERTICAL }
            .let { binding.rcvSelectVenueList.layoutManager = it }

        SelectVenueListAdapter(ArrayList(venueList)) {
            mVM.selectVenue(it)
        }.let { binding.rcvSelectVenueList.adapter = it }
    }

    override fun addViewListeners() {

    }
}
