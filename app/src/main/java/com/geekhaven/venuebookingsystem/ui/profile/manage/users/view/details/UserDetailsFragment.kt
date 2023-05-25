package com.geekhaven.venuebookingsystem.ui.profile.manage.users.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.databinding.FragmentUserDetailsBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class UserDetailsFragment : AbsFragment<FragmentUserDetailsBinding, UserDetailsVM>() {

    override val fragmentName: String = UserDetailsFragment::class.java.simpleName
    override val vmClass: Class<UserDetailsVM> = UserDetailsVM::class.java

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentUserDetailsBinding.inflate(inflater, container, false)

    override fun addLiveDataObservers() {
        mVM.getUserDetails().observe(viewLifecycleOwner) {
            binding.tvUserDetailsName.text = it.name
            binding.tvUserDetailsEmail.text = it.email
            binding.tvUserDetailsParent.text = it.parentEmail
            binding.tvUserDetailsPermission.text = if(it.requireParentPermission) "Yes" else "No"
            binding.tvUserDetailsAdmin.text = if(it.isAdmin) "Yes" else "No"
        }
    }

    override fun addViewListeners() {
        binding.btnUserDetailsEdit.setOnClickListener { mVM.editUserDetails() }
        binding.btnUserDetailsRemove.setOnClickListener { mVM.maybeRemoveUser() }
    }
}
