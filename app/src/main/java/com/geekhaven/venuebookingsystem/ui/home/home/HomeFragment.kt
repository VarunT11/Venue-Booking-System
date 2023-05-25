package com.geekhaven.venuebookingsystem.ui.home.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import com.geekhaven.venuebookingsystem.databinding.FragmentHomeBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.utils.toDateString
import com.geekhaven.venuebookingsystem.utils.toTimeString
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class HomeFragment : AbsFragment<FragmentHomeBinding, HomeVM>() {

  override val fragmentName: String = HomeFragment::class.java.name
  override val vmClass: Class<HomeVM> = HomeVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentHomeBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getEventDate().observe(viewLifecycleOwner) { binding.inputHomeBookingDate.editText?.setText(it.toDateString()) }
    mVM.getStartTime().observe(viewLifecycleOwner) { binding.inputHomeBookingStartTime.editText?.setText(it.toTimeString()) }
    mVM.getEndTime().observe(viewLifecycleOwner) { binding.inputHomeBookingEndTime.editText?.setText(it.toTimeString()) }

    mVM.getInputTitleError().observe(viewLifecycleOwner) { binding.inputHomeBookingTitle.error = it }
    mVM.getInputDescriptionError().observe(viewLifecycleOwner) { binding.inputHomeBookingDescription.error = it }
    mVM.getInputDateError().observe(viewLifecycleOwner) { binding.inputHomeBookingDate.error = it }
    mVM.getInputStartTimeError().observe(viewLifecycleOwner) { binding.inputHomeBookingStartTime.error = it }
    mVM.getInputEndTimeError().observe(viewLifecycleOwner) { binding.inputHomeBookingEndTime.error = it }
    mVM.getInputVenueTypeError().observe(viewLifecycleOwner) { binding.inputHomeBookingVenueType.error = it }
    mVM.getInputBookingTypeError().observe(viewLifecycleOwner) { binding.inputHomeBookingType.error = it }
    mVM.getInputExpectedStrengthError().observe(viewLifecycleOwner) { binding.inputHomeBookingExpectedStrength.error = it }

    mVM.getVenueTypeList().observe(viewLifecycleOwner) {
      val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, it.map { type->type.toDisplayString() })
      (binding.inputHomeBookingVenueType.editText as? MaterialAutoCompleteTextView)
        ?.setAdapter(arrayAdapter)
    }

    mVM.getBookingTypeList().observe(viewLifecycleOwner) {
      val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, it.map { type->type.toDisplayString() })
      (binding.inputHomeBookingType.editText as? MaterialAutoCompleteTextView)
        ?.setAdapter(arrayAdapter)
    }
  }

  override fun addViewListeners() {
    binding.btnSearchVenues.setOnClickListener {
      mVM.maybeInitiateBooking(
        title = getTitle(),
        description = getDescription(),
        expectedStrength = getExpectedStrength()
      )
    }

    binding.inputHomeBookingDate.editText?.setOnClickListener { mVM.setEventDate() }
    binding.inputHomeBookingStartTime.editText?.setOnClickListener { mVM.setStartTime() }
    binding.inputHomeBookingEndTime.editText?.setOnClickListener { mVM.setEndTime() }

    (binding.inputHomeBookingVenueType.editText as? AutoCompleteTextView)
      ?.onItemClickListener = VenueTypeItemSelectedListener()

    (binding.inputHomeBookingType.editText as? AutoCompleteTextView)
      ?.onItemClickListener = BookingTypeItemSelectedListener()

    binding.inputHomeBookingTitle.editText?.addTextChangedListener { mVM.handleInputTitleChanged() }
    binding.inputHomeBookingDescription.editText?.addTextChangedListener { mVM.handleInputDescriptionChanged() }
    binding.inputHomeBookingDate.editText?.addTextChangedListener { mVM.handleInputDateChanged() }
    binding.inputHomeBookingStartTime.editText?.addTextChangedListener { mVM.handleInputStartTimeChanged() }
    binding.inputHomeBookingEndTime.editText?.addTextChangedListener { mVM.handleInputEndTimeChanged() }
    binding.inputHomeBookingVenueType.editText?.addTextChangedListener { mVM.handleInputVenueTypeChanged() }
    binding.inputHomeBookingType.editText?.addTextChangedListener { mVM.handleInputBookingTypeChanged() }
    binding.inputHomeBookingExpectedStrength.editText?.addTextChangedListener { mVM.handleInputExpectedStrengthChanged() }
  }

  private fun getTitle() = binding.inputHomeBookingTitle.editText?.text.toString()
  private fun getDescription() = binding.inputHomeBookingDescription.editText?.text.toString()
  private fun getExpectedStrength() = binding.inputHomeBookingExpectedStrength.editText?.text.toString()

  inner class VenueTypeItemSelectedListener: AdapterView.OnItemClickListener {
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      mVM.updateVenueType(position)
    }
  }

  inner class BookingTypeItemSelectedListener: AdapterView.OnItemClickListener {
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      mVM.updateBookingType(position)
    }
  }
}
