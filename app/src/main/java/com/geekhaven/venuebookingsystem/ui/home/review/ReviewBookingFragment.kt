package com.geekhaven.venuebookingsystem.ui.home.review

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.databinding.FragmentReviewBookingBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.utils.toDateString
import com.geekhaven.venuebookingsystem.utils.toTimeString

class ReviewBookingFragment : AbsFragment<FragmentReviewBookingBinding, ReviewBookingVM>() {

    override val fragmentName: String = ReviewBookingFragment::class.java.simpleName
    override val vmClass: Class<ReviewBookingVM> = ReviewBookingVM::class.java

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentReviewBookingBinding.inflate(inflater, container, false)

    override fun addLiveDataObservers() {
        mVM.getBookingDetails().observe(viewLifecycleOwner) {
            binding.tvReviewBookingTitle.text = it.title
            binding.tvReviewBookingDescription.text = it.description
            binding.tvReviewBookingExpectedStrength.text = it.expectedStrength.toString()
            binding.tvReviewBookingType.text = it.bookingType.toDisplayString()
            binding.tvReviewBookingDate.text = it.eventStartTime.toDateString()
            "${it.eventStartTime.toTimeString()} - ${it.eventEndTime.toTimeString()}".also { text -> binding.tvReviewBookingTime.text = text }
        }

        mVM.getVenueType().observe(viewLifecycleOwner) {
            binding.tvReviewBookingVenueType.text = it.toDisplayString()
        }

        mVM.getLocationDetails().observe(viewLifecycleOwner) {
            binding.tvReviewBookingLocation.text = it
        }
    }

    override fun addViewListeners() {
        binding.btnReviewBookingConfirm.setOnClickListener { mVM.confirmBooking() }
        binding.btnReviewBookingChangeTime.setOnClickListener { mVM.changeEventTime() }
    }
}
