package com.geekhaven.venuebookingsystem.ui.home.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.ui.VenueListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentBuildingHomeBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class BuildingHomeFragment : AbsFragment<FragmentBuildingHomeBinding, BuildingHomeVM>() {

  override val fragmentName: String = BuildingHomeFragment::class.java.simpleName
  override val vmClass: Class<BuildingHomeVM> = BuildingHomeVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentBuildingHomeBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {}

  override fun addViewListeners() {}

  private fun handleVenueClick(id: String) {}

}
