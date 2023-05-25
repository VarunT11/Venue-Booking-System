package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.geekhaven.venuebookingsystem.databinding.FragmentAddBuildingBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.api.models.BuildingResponse

class EditBuildingFragment: AbsFragment<FragmentAddBuildingBinding, EditBuildingVM>() {

  override val fragmentName: String = EditBuildingFragment::class.java.simpleName
  override val vmClass: Class<EditBuildingVM> = EditBuildingVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentAddBuildingBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getBuilding().observe(viewLifecycleOwner) {building ->
      building?.name.let { binding.inputAddBuildingName.editText?.setText(it) }
    }

    mVM.getInputNameError().observe(viewLifecycleOwner) {
      binding.inputAddBuildingName.error = it
    }
  }

  override fun addViewListeners() {
    binding.btnAddBuildingSubmit
      .also { it.text = "Edit Details" }
      .setOnClickListener {
      mVM.maybeUpdateBuilding(getInputName())
    }

    binding.inputAddBuildingName.editText?.addTextChangedListener { mVM.handleInputNameChanged() }
  }

  private fun getInputName() = binding.inputAddBuildingName.editText?.text.toString()
}
