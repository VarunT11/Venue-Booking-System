package com.geekhaven.venuebookingsystem.ui.profile.manage.buildings.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.geekhaven.venuebookingsystem.databinding.FragmentAddBuildingBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.api.models.BuildingResponse

class EditBuildingFragment: AbsFragment<FragmentAddBuildingBinding, EditBuildingVM>() {

  override val fragmentName: String = EditBuildingFragment::class.java.simpleName
  override val vmClass: Class<EditBuildingVM> = EditBuildingVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentAddBuildingBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {}

  override fun addViewListeners() {}
}
