package com.geekhaven.venuebookingsystem.ui.profile.manage.users.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.databinding.FragmentAddUsersBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class EditUserFragment: AbsFragment<FragmentAddUsersBinding, EditUserVM>() {

    override val fragmentName: String = EditUserFragment::class.java.simpleName
    override val vmClass: Class<EditUserVM> = EditUserVM::class.java

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAddUsersBinding.inflate(inflater, container, false)

    override fun addLiveDataObservers() {
        binding.inputAddUserEmail.isEnabled = false

        mVM.getInputNameError().observe(viewLifecycleOwner) {
            binding.inputAddUserName.error = it
        }

        mVM.getInputParentEmailError().observe(viewLifecycleOwner) {
            binding.inputAddUserAuthority.error = it
        }

        mVM.getUserDetails().observe(viewLifecycleOwner) {
            binding.inputAddUserEmail.editText?.setText(it.email)
            binding.inputAddUserName.editText?.setText(it.name)
            binding.inputAddUserAuthority.editText?.setText(it.parentEmail)

            val requireParentPermissionCheckedId =
                if(it.requireParentPermission)
                    R.id.btnAddUserRequireParentPermissionYes
                else
                    R.id.btnAddUserRequireParentPermissionNo

            val isAdminCheckedId =
                if(it.isAdmin)
                    R.id.btnAddUserIsAdminYes
                else
                    R.id.btnAddUserIsAdminNo

            binding.inputAddUserParentPermissionRequiredSwitch.check(requireParentPermissionCheckedId)
            binding.inputAddUserIsAdminSwitch.check(isAdminCheckedId)
        }
    }

    override fun addViewListeners() {
        binding.inputAddUserName.editText?.addTextChangedListener { mVM.handleInputNameChanged() }
        binding.inputAddUserAuthority.editText?.addTextChangedListener { mVM.handleInputParentEmailChanged() }

        binding.btnAddUserSubmit
            .apply { "Edit User Details".also { text = it } }
            .setOnClickListener {
                mVM.maybeUpdateUser(getInputName(), getParentEmail(), isParentPermissionRequired(), isAdmin())
            }
    }

    private fun getInputName() = binding.inputAddUserName.editText?.text.toString()
    private fun getParentEmail() = binding.inputAddUserAuthority.editText?.text.toString()
    private fun isParentPermissionRequired() = binding.inputAddUserParentPermissionRequiredSwitch.checkedButtonId == R.id.btnAddUserRequireParentPermissionYes
    private fun isAdmin() = binding.inputAddUserIsAdminSwitch.checkedButtonId == R.id.btnAddUserIsAdminYes
}
