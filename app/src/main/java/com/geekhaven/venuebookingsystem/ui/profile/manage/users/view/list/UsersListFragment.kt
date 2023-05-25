package com.geekhaven.venuebookingsystem.ui.profile.manage.users.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.models.items.UserListItem
import com.geekhaven.venuebookingsystem.adapter.ui.UserListAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentUsersListBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class UsersListFragment : AbsFragment<FragmentUsersListBinding, UsersListVM>() {

  override val fragmentName: String = UsersListFragment::class.java.simpleName
  override val vmClass: Class<UsersListVM> = UsersListVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentUsersListBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getUsersList().observe(viewLifecycleOwner) { renderUsersList(it) }
  }

  override fun addViewListeners() {

  }

  private fun renderUsersList(users: List<UserListItem>) {
    LinearLayoutManager(requireContext())
      .apply { orientation = LinearLayoutManager.VERTICAL }
      .let { binding.rcvUsersList.layoutManager = it }

    UserListAdapter(
      itemsList = ArrayList(users),
      onClick = { mVM.handleUserSelect(it) },
      onEditSelect = { mVM.handleUserEditSelect(it) },
      onDeleteSelect = { mVM.handleUserRemoveSelect(it) }
    )
      .let { binding.rcvUsersList.adapter = it }
  }

}
