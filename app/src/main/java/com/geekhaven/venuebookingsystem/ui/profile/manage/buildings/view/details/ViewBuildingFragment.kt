package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.databinding.FragmentViewBuildingBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class ViewBuildingFragment : AbsFragment<FragmentViewBuildingBinding, ViewBuildingVM>() {

  override val fragmentName: String = ViewBuildingFragment::class.java.simpleName
  override val vmClass: Class<ViewBuildingVM> = ViewBuildingVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentViewBuildingBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getBuildingDetails().observe(viewLifecycleOwner) {
      binding.tvViewBuildingName.text = it.name
    }
  }

  override fun addViewListeners() {
    binding.btnViewBuildingEdit.setOnClickListener { mVM.editBuildingDetails() }
    binding.btnViewBuildingDelete.setOnClickListener { mVM.maybeRemoveBuilding() }
  }
}
