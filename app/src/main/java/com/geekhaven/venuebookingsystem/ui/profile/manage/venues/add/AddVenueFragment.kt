package com.geekhaven.venuebookingsystem.ui.profile.manage.venues.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.databinding.FragmentAddVenueBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class AddVenueFragment : AbsFragment<FragmentAddVenueBinding, AddVenueVM>() {

  override val fragmentName: String = AddVenueFragment::class.java.simpleName
  override val vmClass: Class<AddVenueVM> = AddVenueVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentAddVenueBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
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

    binding.btnAddVenueSubmit.setOnClickListener {
      mVM.maybeAddNewVenue(
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

  inner class BuildingListItemSelectedListener: OnItemClickListener {
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      mVM.setSelectedBuilding(position)
    }
  }

  inner class VenueTypeItemSelectedListener: OnItemClickListener {
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      mVM.setSelectedVenueType(position)
    }
  }

  inner class UserListItemSelectedListener: OnItemClickListener {
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      mVM.setSelectedUser(position)
    }
  }
}
