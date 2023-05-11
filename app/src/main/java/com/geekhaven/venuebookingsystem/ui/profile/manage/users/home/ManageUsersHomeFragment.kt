package com.geekhaven.venuebookingsystem.ui.profile.manage.users.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.models.items.ManageOptionItem
import com.geekhaven.venuebookingsystem.adapter.ui.ManageOptionAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentManageUsersHomeBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class ManageUsersHomeFragment : AbsFragment<FragmentManageUsersHomeBinding, ManageUsersHomeVM>() {

  override val fragmentName: String = ManageUsersHomeFragment::class.java.simpleName
  override val vmClass: Class<ManageUsersHomeVM> = ManageUsersHomeVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentManageUsersHomeBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getOptionsList().observe(viewLifecycleOwner) { renderOptionList(it) }
  }

  override fun addViewListeners() {

  }

  private fun renderOptionList(optionsList: ArrayList<ManageOptionItem>){
    LinearLayoutManager(requireContext())
      .apply { orientation = GridLayoutManager.VERTICAL }
      .also { binding.rcvManageUsersOptions.layoutManager = it }

    ManageOptionAdapter(requireContext(), optionsList) { mVM.handleOptionSelected(it) }
      .also { binding.rcvManageUsersOptions.adapter = it }
  }

}
