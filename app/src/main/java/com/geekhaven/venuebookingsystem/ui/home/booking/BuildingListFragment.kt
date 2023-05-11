package com.geekhaven.venuebookingsystem.ui.home.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.ui.BuildingListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentBuildingListBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class BuildingListFragment : AbsFragment<FragmentBuildingListBinding, BuildingListVM>() {

  override val fragmentName: String = BuildingListFragment::class.java.simpleName
  override val vmClass: Class<BuildingListVM> = BuildingListVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentBuildingListBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {}

  override fun addViewListeners() {}

}
