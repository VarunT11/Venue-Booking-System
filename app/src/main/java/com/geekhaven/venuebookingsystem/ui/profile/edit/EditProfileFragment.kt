package com.geekhaven.venuebookingsystem.ui.profile.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.geekhaven.venuebookingsystem.databinding.FragmentEditProfileBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class EditProfileFragment : AbsFragment<FragmentEditProfileBinding, EditProfileVM>() {

  override val fragmentName: String = EditProfileFragment::class.java.simpleName
  override val vmClass: Class<EditProfileVM> = EditProfileVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentEditProfileBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getUserName().observe(viewLifecycleOwner) {
      binding.inputEditProfileName.editText?.setText(it)
    }

    mVM.getInputNameError().observe(viewLifecycleOwner) {
      binding.inputEditProfileName.error = it
    }
  }

  override fun addViewListeners() {
    binding.btnSaveChangesEditProfile.setOnClickListener {
      mVM.maybeUpdateUserName(getInputName())
    }

    binding.inputEditProfileName.editText?.addTextChangedListener {
      mVM.handleInputNameChanged()
    }
  }

  private fun getInputName() =
    binding.inputEditProfileName.editText?.text.toString()

}
