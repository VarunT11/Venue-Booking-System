package com.geekhaven.venuebookingsystem.ui.profile.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekhaven.venuebookingsystem.adapter.ui.ProfileSettingsAdapter
import com.geekhaven.venuebookingsystem.databinding.FragmentProfileBinding
import com.geekhaven.venuebookingsystem.models.data.User
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment
import com.geekhaven.venuebookingsystem.utils.getRolesText
import com.squareup.picasso.Picasso

class ProfileFragment : AbsFragment<FragmentProfileBinding, ProfileVM>() {

  override val fragmentName: String = ProfileFragment::class.java.simpleName
  override val vmClass: Class<ProfileVM> = ProfileVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentProfileBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mainVM.getCurrentUserDetails().observe(viewLifecycleOwner) {
      renderUserDetails(it)
      mVM.getParentUserDetails(it.parentEmail ?: "")
    }

    mVM.getParentUserDetails().observe(viewLifecycleOwner) {
      binding.tvProfileAuthorityName.text = it.name
    }

    mVM.getSettingsList().observe(viewLifecycleOwner) { settingsList ->
      LinearLayoutManager(context)
        .apply { orientation = LinearLayoutManager.VERTICAL }
        .let { binding.rcvProfileSettings.layoutManager = it }

      ProfileSettingsAdapter(requireContext(), settingsList as ArrayList) {
        mVM.handleSettingsOptionSelected(it)
      }.let { adapter -> binding.rcvProfileSettings.adapter = adapter }
    }
  }

  private fun renderUserDetails(user: User) {
    binding.tvProfileUserName.text = user.name
    binding.tvProfileUserEmail.text = user.email

    renderRoleDetails(user)

    Picasso.get()
      .load(Uri.parse(user.photoUrl))
      .into(binding.imgProfileUserPhoto)
  }

  private fun renderRoleDetails(user: User) {
    binding.tvProfileRole.text = getRolesText(user.isAdmin, user.isAuthority)
  }

  override fun addViewListeners() {}
}
