package com.geekhaven.venuebookingsystem.ui.profile.manage.venues.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.adapter.ui.ManageOptionAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentManageVenuesHomeBinding
import com.geekhaven.venuebookingsystem.adapter.models.items.ManageOptionItem
import com.geekhaven.venuebookingsystem.adapter.models.type.ManageOptionType
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class ManageVenuesHomeFragment : AbsFragment<FragmentManageVenuesHomeBinding, ManageVenuesHomeVM>() {

  override val fragmentName: String = ManageVenuesHomeFragment::class.java.simpleName
  override val vmClass: Class<ManageVenuesHomeVM> = ManageVenuesHomeVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentManageVenuesHomeBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getOptionsList().observe(viewLifecycleOwner) { renderOptionsList(it) }
  }

  override fun addViewListeners() {

  }

  private fun renderOptionsList(options: ArrayList<ManageOptionItem>){
    LinearLayoutManager(requireContext())
      .apply { orientation = GridLayoutManager.VERTICAL }
      .also { binding.rcvManageVenuesOptions.layoutManager = it }

    ManageOptionAdapter(requireContext(), options) { mVM.handleOptionSelected(it) }
      .also { binding.rcvManageVenuesOptions.adapter = it }
  }
}
