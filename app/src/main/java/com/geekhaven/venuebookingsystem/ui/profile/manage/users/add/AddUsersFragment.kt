package com.geekhaven.venuebookingsystem.ui.profile.manage.users.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.geekhaven.venuebookingsystem.R
import com.geekhaven.venuebookingsystem.databinding.FragmentAddUsersBinding
import com.geekhaven.venuebookingsystem.ui.abs.AbsFragment

class AddUsersFragment : AbsFragment<FragmentAddUsersBinding, AddUserVM>() {

  override val fragmentName: String = AddUsersFragment::class.java.simpleName
  override val vmClass: Class<AddUserVM> = AddUserVM::class.java

  override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
    FragmentAddUsersBinding.inflate(inflater, container, false)

  override fun addLiveDataObservers() {
    mVM.getInputEmailError().observe(viewLifecycleOwner) { binding.inputAddUserEmail.error = it }
    mVM.getInputNameError().observe(viewLifecycleOwner) { binding.inputAddUserName.error = it }
    mVM.getInputParentEmailError().observe(viewLifecycleOwner) { binding.inputAddUserAuthority.error = it }
  }

  override fun addViewListeners() {
    binding.btnAddUserSubmit.setOnClickListener { mVM.maybeAddUser(
      email = getInputEmail(),
      name = getInputName(),
      parentEmail = getParentEmail(),
      requirePermission = isParentPermissionRequired(),
      isAdmin = isAdmin()
    ) }

    binding.inputAddUserEmail.editText?.addTextChangedListener { mVM.handleInputEmailChanged() }
    binding.inputAddUserName.editText?.addTextChangedListener { mVM.handleInputNameChanged() }
    binding.inputAddUserAuthority.editText?.addTextChangedListener { mVM.handleInputParentEmailChanged() }
  }

  private fun getInputEmail() = binding.inputAddUserEmail.editText?.text.toString()
  private fun getInputName() = binding.inputAddUserName.editText?.text.toString()
  private fun getParentEmail() = binding.inputAddUserAuthority.editText?.text.toString()
  private fun isParentPermissionRequired() = binding.inputAddUserParentPermissionRequiredSwitch.checkedButtonId == R.id.btnAddUserRequireParentPermissionYes
  private fun isAdmin() = binding.inputAddUserIsAdminSwitch.checkedButtonId == R.id.btnAddUserIsAdminYes

}
