package com.geekhaven.venuebookingsystem.ui.profile.manage.users.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.databinding.FragmentAddUsersBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class AddUsersFragment : AbsFragment<FragmentAddUsersBinding, AddUserVM>() {

  override val fragmentName: String = AddUsersFragment::class.java.simpleName
  override val vmClass: Class<AddUserVM> = AddUserVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentAddUsersBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {

  }

  override fun addViewListeners() {

  }

}
