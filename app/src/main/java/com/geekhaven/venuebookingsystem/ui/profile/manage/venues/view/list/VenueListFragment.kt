package com.geekhaven.venuebookingsystem.ui.profile.manage.venues.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.models.items.VenueListItem
import com.geekhaven.venuebookingsystem.adapter.ui.VenueListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentVenueListBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class VenueListFragment : AbsFragment<FragmentVenueListBinding, VenueListVM>() {

    override val fragmentName: String = VenueListFragment::class.java.simpleName
    override val vmClass: Class<VenueListVM> = VenueListVM::class.java

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentVenueListBinding.inflate(inflater, container, false)

    override fun addLiveDataObservers() {
        mVM.getVenueList().observe(viewLifecycleOwner) { renderVenueList(it) }
    }

    private fun renderVenueList(venues: List<VenueListItem>) {
        LinearLayoutManager(requireContext())
            .apply { orientation = LinearLayoutManager.VERTICAL }
            .let { binding.rcvVenuesList.layoutManager = it }

        VenueListAdapter(
            venueResponseList = ArrayList(venues),
            onClick = { mVM.handleUserSelect(it) },
            onEditSelect = { mVM.handleEditSelect(it) },
            onRemoveSelect = { mVM.handleRemoveSelect(it) }
        )
            .let { binding.rcvVenuesList.adapter = it }
    }

    override fun addViewListeners() {

    }
}
