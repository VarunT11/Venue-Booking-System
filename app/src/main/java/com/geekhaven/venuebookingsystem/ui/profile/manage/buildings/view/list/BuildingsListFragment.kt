package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.ui.BuildingsListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentBuildingsListBinding
import com.geekhaven.venuebookingsystem.models.data.Building
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class BuildingsListFragment : AbsFragment<FragmentBuildingsListBinding, BuildingsListVM>() {

  override val fragmentName: String = BuildingsListVM::class.java.simpleName
  override val vmClass: Class<BuildingsListVM> = BuildingsListVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentBuildingsListBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getBuildingsList().observe(viewLifecycleOwner) { renderBuildingsList(it) }
  }

  override fun addViewListeners() {

  }

  private fun renderBuildingsList(buildings: List<Building>) {
    LinearLayoutManager(requireContext())
      .apply { orientation = LinearLayoutManager.VERTICAL }
      .let { binding.rcvBuildingsList.layoutManager = it }

    BuildingsListAdapter(
      itemsList = ArrayList(buildings),
      onClick = { mVM.handleBuildingSelect(it) },
      onEditSelect = { mVM.handleBuildingEditSelect(it) },
      onDeleteSelect = { mVM.handleBuildingRemoveSelect(it) }
    )
      .let { binding.rcvBuildingsList.adapter = it }
  }

}
