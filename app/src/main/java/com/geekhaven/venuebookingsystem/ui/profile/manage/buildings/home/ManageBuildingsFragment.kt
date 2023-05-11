package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.models.items.ManageOptionItem
import com.geekhaven.venuebookingsystem.adapter.ui.ManageOptionAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentManageBuildingsBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class ManageBuildingsFragment : AbsFragment<FragmentManageBuildingsBinding, ManageBuildingsVM>() {

  override val fragmentName: String = ManageBuildingsFragment::class.java.simpleName
  override val vmClass: Class<ManageBuildingsVM> = ManageBuildingsVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentManageBuildingsBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getOptionsList().observe(viewLifecycleOwner) { renderOptionsList(it) }
  }

  override fun addViewListeners() {

  }

  private fun renderOptionsList(optionsList: ArrayList<ManageOptionItem>){
    LinearLayoutManager(requireContext())
      .apply { orientation = GridLayoutManager.VERTICAL }
      .also { binding.rcvManageBuildings.layoutManager = it }

    ManageOptionAdapter(requireContext(), optionsList) { mVM.handleOptionSelected(it) }
      .also { binding.rcvManageBuildings.adapter = it }
  }
}
