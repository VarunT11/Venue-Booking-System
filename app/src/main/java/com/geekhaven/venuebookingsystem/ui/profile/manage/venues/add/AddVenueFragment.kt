package com.geekhaven.venuebookingsystem.ui.profile.manage.venues.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.databinding.FragmentAddVenueBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class AddVenueFragment : AbsFragment<FragmentAddVenueBinding, AddVenueVM>() {

  override val fragmentName: String = AddVenueFragment::class.java.simpleName
  override val vmClass: Class<AddVenueVM> = AddVenueVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentAddVenueBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    TODO("Not yet implemented")
  }

  override fun addViewListeners() {
    TODO("Not yet implemented")
  }

}
