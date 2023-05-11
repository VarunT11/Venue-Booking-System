package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.geekhaven.venuebookingsystem.databinding.FragmentAddBuildingBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class AddBuildingFragment : AbsFragment<FragmentAddBuildingBinding, AddBuildingVM>() {

  override val fragmentName: String = AddBuildingFragment::class.java.simpleName
  override val vmClass: Class<AddBuildingVM> = AddBuildingVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentAddBuildingBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getInputNameError().observe(viewLifecycleOwner) {
      binding.inputAddBuildingName.error = it
    }
  }

  override fun addViewListeners() {
    binding.btnAddBuildingSubmit.setOnClickListener {
      mVM.maybeAddBuilding(getInputName())
    }

    binding.inputAddBuildingName.editText?.addTextChangedListener { mVM.handleInputNameChanged() }
  }

  private fun getInputName() = binding.inputAddBuildingName.editText?.text.toString()
}
