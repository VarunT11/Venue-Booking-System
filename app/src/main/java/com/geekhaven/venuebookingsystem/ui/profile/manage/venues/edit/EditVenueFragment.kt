package com.geekhaven.venuebookingsystem.ui.profile.manage.venues.edit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.databinding.FragmentAddVenueBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class EditVenueFragment: AbsFragment<FragmentAddVenueBinding, EditVenueVM>() {

    override val fragmentName: String = EditVenueFragment::class.java.simpleName
    override val vmClass: Class<EditVenueVM> = EditVenueVM::class.java

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAddVenueBinding.inflate(inflater, container, false)

    override fun addLiveDataObservers() {
        mVM.getCurrentVenueDetails().observe(viewLifecycleOwner) {
            binding.inputAddVenueName.editText?.setText(it.name)
            binding.inputAddVenueFloor.editText?.setText(it.floorNumber.toString())
            binding.inputAddVenueCapacity.editText?.setText(it.seatingCapacity.toString())

            val isAccessible =
                if (it.isAccessible)
                    R.id.btnAddVenueIsAccessibleYes
                else
                    R.id.btnAddVenueIsAccessibleNo

            val hasAirConditioner =
                if (it.hasAirConditioner)
                    R.id.btnAddVenueHasAirConditionerYes
                else
                    R.id.btnAddVenueHasAirConditionerNo

            val hasProjector =
                if (it.hasProjectors)
                    R.id.btnAddVenueHasProjectorYes
                else
                    R.id.btnAddVenueHasProjectorNo

            val hasSpeakers =
                if (it.hasSpeakers)
                    R.id.btnAddVenueHasSpeakersYes
                else
                    R.id.btnAddVenueHasSpeakersNo

            val hasWhiteboard =
                if (it.hasWhiteboard)
                    R.id.btnAddVenueHasWhiteboardYes
                else
                    R.id.btnAddVenueHasWhiteboardNo

            binding.inputAddVenueIsAccessibleSwitch.check(isAccessible)
            binding.inputAddVenueSpeakersSwitch.check(hasSpeakers)
            binding.inputAddVenueProjectorSwitch.check(hasProjector)
            binding.inputAddVenueAirConditionerSwitch.check(hasAirConditioner)
            binding.inputAddVenueWhiteboardSwitch.check(hasWhiteboard)

            mVM.getVenueTypeList().value!!.indexOf(it.venueType).let { position -> mVM.setSelectedVenueType(position) }
            (binding.inputAddVenueType.editText as? MaterialAutoCompleteTextView)?.setText(it.venueType.toDisplayString(), false)

            mVM.getBuildingsList().value!!.map { building -> building.id }.indexOf(it.buildingId).let { position -> mVM.setSelectedBuilding(position) }
            val buildingName = mVM.getBuildingsList().value!!.filter { building -> building.id == it.buildingId }[0].name
            (binding.inputAddVenueBuilding.editText as? MaterialAutoCompleteTextView)?.setText(buildingName, false)

            mVM.getUsersList().value!!.map { user -> user.email }.indexOf(it.authorityId).let { position -> mVM.setSelectedUser(position) }
            val userName = mVM.getUsersList().value!!.filter { user -> user.email == it.authorityId }[0].name
            (binding.inputAddVenueAuthority.editText as? MaterialAutoCompleteTextView)?.setText(userName, false)
        }

        mVM.getBuildingsList().observe(viewLifecycleOwner) {
            val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, it.map { building->building.name })
            (binding.inputAddVenueBuilding.editText as? MaterialAutoCompleteTextView)
                ?.setAdapter(arrayAdapter)
        }

        mVM.getVenueTypeList().observe(viewLifecycleOwner) {
            val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, it.map { type->type.toDisplayString() })
            (binding.inputAddVenueType.editText as? MaterialAutoCompleteTextView)
                ?.setAdapter(arrayAdapter)
        }

        mVM.getUsersList().observe(viewLifecycleOwner) {
            val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, it.map { user->user.name })
            (binding.inputAddVenueAuthority.editText as? MaterialAutoCompleteTextView)
                ?.setAdapter(arrayAdapter)
        }

        mVM.getInputNameError().observe(viewLifecycleOwner) {
            binding.inputAddVenueName.error = it
        }

        mVM.getInputBuildingError().observe(viewLifecycleOwner) {
            binding.inputAddVenueBuilding.error = it
        }

        mVM.getInputFloorError().observe(viewLifecycleOwner) {
            binding.inputAddVenueFloor.error = it
        }

        mVM.getInputVenueTypeError().observe(viewLifecycleOwner) {
            binding.inputAddVenueType.error = it
        }

        mVM.getInputAuthorityError().observe(viewLifecycleOwner) {
            binding.inputAddVenueAuthority.error = it
        }

        mVM.getInputSeatingCapacity().observe(viewLifecycleOwner) {
            binding.inputAddVenueCapacity.error = it
        }
    }

    override fun addViewListeners() {
        (binding.inputAddVenueBuilding.editText as? AutoCompleteTextView)
            ?.onItemClickListener = BuildingListItemSelectedListener()

        (binding.inputAddVenueType.editText as? AutoCompleteTextView)
            ?.onItemClickListener = VenueTypeItemSelectedListener()

        (binding.inputAddVenueAuthority.editText as? AutoCompleteTextView)
            ?.onItemClickListener = UserListItemSelectedListener()

        binding.inputAddVenueName.editText?.addTextChangedListener { mVM.handleInputNameChanged() }
        binding.inputAddVenueBuilding.editText?.addTextChangedListener { mVM.handleInputBuildingChanged() }
        binding.inputAddVenueFloor.editText?.addTextChangedListener { mVM.handleInputFloorChanged() }
        binding.inputAddVenueType.editText?.addTextChangedListener { mVM.handleInputVenueTypeChanged() }
        binding.inputAddVenueAuthority.editText?.addTextChangedListener { mVM.handleInputAuthorityChanged() }
        binding.inputAddVenueCapacity.editText?.addTextChangedListener { mVM.handleSeatingCapacityChanged() }

        binding.btnAddVenueSubmit
            .apply { "Edit Details".also { text = it } }
            .setOnClickListener {
            mVM.maybeUpdateVenue(
                name = getInputName(),
                floor = getInputFloor(),
                capacity = getInputSeatingCapacity(),
                isAccessible = isAccessible(),
                hasAirConditioner = hasAirConditioner(),
                hasProjector = hasProjector(),
                hasSpeaker = hasSpeaker(),
                hasWhiteboard = hasWhiteboard(),
            )
        }
    }

    private fun getInputName() = binding.inputAddVenueName.editText?.text.toString()
    private fun getInputFloor() = binding.inputAddVenueFloor.editText?.text.toString()
    private fun getInputSeatingCapacity() = binding.inputAddVenueCapacity.editText?.text.toString()
    private fun isAccessible() = binding.inputAddVenueIsAccessibleSwitch.checkedButtonId == R.id.btnAddVenueIsAccessibleYes
    private fun hasAirConditioner() = binding.inputAddVenueAirConditionerSwitch.checkedButtonId == R.id.btnAddVenueHasAirConditionerYes
    private fun hasProjector() = binding.inputAddVenueProjectorSwitch.checkedButtonId == R.id.btnAddVenueHasProjectorYes
    private fun hasSpeaker() = binding.inputAddVenueSpeakersSwitch.checkedButtonId == R.id.btnAddVenueHasSpeakersYes
    private fun hasWhiteboard() = binding.inputAddVenueWhiteboardSwitch.checkedButtonId == R.id.btnAddVenueHasWhiteboardYes

    inner class BuildingListItemSelectedListener: AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mVM.setSelectedBuilding(position)
        }
    }

    inner class VenueTypeItemSelectedListener: AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mVM.setSelectedVenueType(position)
        }
    }

    inner class UserListItemSelectedListener: AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mVM.setSelectedUser(position)
        }
    }

}
