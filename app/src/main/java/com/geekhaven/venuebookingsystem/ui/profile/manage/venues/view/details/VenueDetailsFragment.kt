package com.geekhaven.venuebookingsystem.ui.profile.manage.venues.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.databinding.FragmentVenueDetailsBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class VenueDetailsFragment : AbsFragment<FragmentVenueDetailsBinding, VenueDetailsVM>() {

    override val fragmentName: String = VenueDetailsFragment::class.java.simpleName
    override val vmClass: Class<VenueDetailsVM> = VenueDetailsVM::class.java

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentVenueDetailsBinding.inflate(inflater, container, false)

    override fun addLiveDataObservers() {
        mVM.getVenueDetails().observe(viewLifecycleOwner) {
            binding.tvVenueDetailsName.text = it.name
            binding.tvVenueDetailsFloor.text = it.floorNumber.toString()
            binding.tvVenueDetailsVenueType.text = it.venueType.toDisplayString()
            binding.tvVenueDetailsCapacity.text = it.seatingCapacity.toString()
            binding.tvVenueDetailsAccessible.text = getBooleanString(it.isAccessible)
            binding.tvVenueDetailsAirConditioner.text = getBooleanString(it.hasAirConditioner)
            binding.tvVenueDetailsSpeaker.text = getBooleanString(it.hasSpeakers)
            binding.tvVenueDetailsProjector.text = getBooleanString(it.hasProjectors)
            binding.tvVenueDetailsWhiteboard.text = getBooleanString(it.hasWhiteboard)
        }

        mVM.getBuildingName().observe(viewLifecycleOwner) {
            binding.tvVenueDetailsBuilding.text = it
        }

        mVM.getAuthorityName().observe(viewLifecycleOwner) {
            binding.tvVenueDetailsAuthority.text = it
        }
    }

    override fun addViewListeners() {
        binding.btnVenueDetailsEdit.setOnClickListener {
            mVM.editVenueDetails()
        }

        binding.btnVenueDetailsRemove.setOnClickListener {
            mVM.maybeRemoveVenue()
        }
    }

    private fun getBooleanString(bool: Boolean) = if(bool) "Yes" else "No"

}
