package com.geekhaven.venuebookingsystem.ui.profile.manage.users.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.databinding.FragmentUsersListBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class UsersListFragment : AbsFragment<FragmentUsersListBinding, UsersListVM>() {

  override val fragmentName: String = UsersListFragment::class.java.simpleName
  override val vmClass: Class<UsersListVM> = UsersListVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentUsersListBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {

  }

  override fun addViewListeners() {

  }

}
