package com.geekhaven.venuebookingsystem.adapter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.geekhaven.venuebookingsystem.adapter.AbsListAdapter
import com.geekhaven.venuebookingsystem.adapter.models.items.UserListItem
import com.geekhaven.venuebookingsystem.databinding.ItemUserListBinding

class UserListAdapter(
    private val itemsList: ArrayList<UserListItem>,
    onClick: (String) -> Unit,
    private val onEditSelect: (String) -> Unit,
    private val onDeleteSelect: (String) -> Unit
): AbsListAdapter<ItemUserListBinding, UserListItem>(itemsList, onClick) {

    override fun inflateViewBinding(parent: ViewGroup) =
        ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun getListItemId(position: Int): String = itemsList[position].email

    override fun renderItem(item: UserListItem, binding: ItemUserListBinding) {
        binding.userListItemName.text = item.name
        binding.userListItemRole.text = item.role
        binding.userListItemAuthority.text = item.authority
        binding.btnUserListItemEdit.setOnClickListener { onEditSelect(item.email) }
        binding.btnUserListItemRemove.setOnClickListener { onDeleteSelect(item.email) }
    }
}
