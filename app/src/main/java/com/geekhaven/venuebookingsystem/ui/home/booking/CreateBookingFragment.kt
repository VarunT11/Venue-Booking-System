package com.geekhaven.venuebookingsystem.ui.home.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.geekhaven.venuebookingsystem.databinding.FragmentCreateBookingBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class CreateBookingFragment : AbsFragment<FragmentCreateBookingBinding, CreateBookingVM>() {

  override val fragmentName: String = BuildingListFragment::class.java.simpleName
  override val vmClass: Class<CreateBookingVM> = CreateBookingVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentCreateBookingBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {}

  override fun addViewListeners() {
    binding.btnCreateBookingCreate.setOnClickListener { maybeCreateBooking() }
    binding.inputEventDuration.editText?.addTextChangedListener { updateInputDurationError(null) }
    binding.inputEventExpectedStrength.editText?.addTextChangedListener { updateInputStrengthError(null) }
    binding.inputEventDescription.editText?.addTextChangedListener { updateInputDescriptionError(null) }
  }

  private fun maybeCreateBooking(){
    if(!validateInput()) return
    createBooking()
  }

  private fun createBooking(){
    mVM.createBooking(getInputDuration(), getInputStrength(), getInputDescription())
  }

  private fun validateInput(): Boolean {
    var valid = true
    if(getInputDuration() <= 0){
      valid = false
      updateInputDurationError("Please enter a Valid Duration")
    }
    if(getInputStrength() <= 0){
      valid = false
      updateInputStrengthError("Please enter a Valid Strength")
    }
    if(getInputDescription().isEmpty()){
      valid = false
      updateInputDescriptionError("Please update a Valid Description")
    }
    return valid
  }

  private fun getInputDuration(): Int =
    binding.inputEventDuration.editText!!.text.toString()
      .let { try { it.toInt() } catch (e: Exception){ 0} }

  private fun updateInputDurationError(error: String?) {
    binding.inputEventDuration.error = error
  }

  private fun getInputStrength(): Int =
    binding.inputEventExpectedStrength.editText!!.text.toString()
      .let { try { it.toInt() } catch (e: Exception){ 0} }

  private fun updateInputStrengthError(error: String?) {
    binding.inputEventExpectedStrength.error = error
  }

  private fun getInputDescription() =
    binding.inputEventDescription.editText!!.text.toString()

  private fun updateInputDescriptionError(error: String?) {
    binding.inputEventDescription.error = error
  }
}
