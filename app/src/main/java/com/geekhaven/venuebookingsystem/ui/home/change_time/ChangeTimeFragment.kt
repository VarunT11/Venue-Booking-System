package com.geekhaven.venuebookingsystem.ui.home.change_time

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.calendar.CalendarAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentChangeTimeBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.utils.toDateString
import java.util.Calendar

class ChangeTimeFragment : AbsFragment<FragmentChangeTimeBinding, ChangeTimeVM>() {

    override val fragmentName: String = ChangeTimeFragment::class.java.simpleName
    override val vmClass: Class<ChangeTimeVM> = ChangeTimeVM::class.java

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentChangeTimeBinding.inflate(inflater, container, false)

    override fun addLiveDataObservers() {
        mVM.getVenueDetails().observe(viewLifecycleOwner) {
            binding.tvChangeTimeVenueName.text = it.name
            binding.tvChangeTimeVenueType.text = it.venueType.toDisplayString()
        }

        mVM.getBuildingName().observe(viewLifecycleOwner) {
            binding.tvChangeTimeBuildingName.text = it
        }

        mVM.getBookingDate().observe(viewLifecycleOwner) {
            binding.tvChangeTimeDate.text = it.toDateString()
            updateCalendarViewDate(it)
        }

        val adapter = CalendarAdapter()
        binding.wvChangeTimeCalendar.adapter = adapter

        mVM.getBookingsList().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun addViewListeners() {
        binding.btnChangeTimeChangeDate.setOnClickListener { mVM.changeDate() }
        binding.btnChangeTime.setOnClickListener { mVM.changeTime() }
        binding.btnChangeTimeSaveChanges.setOnClickListener { mVM.saveChanges() }
    }

    private fun updateCalendarViewDate(date: Calendar) {
        val currentDate = binding.wvChangeTimeCalendar.maxDate

        if(currentDate == null) {
            binding.wvChangeTimeCalendar.maxDate = date
            binding.wvChangeTimeCalendar.minDate = date
            return
        }

        if(date > currentDate) {
            binding.wvChangeTimeCalendar.maxDate = date
            binding.wvChangeTimeCalendar.minDate = date
        } else if(date < currentDate) {
            binding.wvChangeTimeCalendar.minDate = date
            binding.wvChangeTimeCalendar.maxDate = date
        }
    }

}
