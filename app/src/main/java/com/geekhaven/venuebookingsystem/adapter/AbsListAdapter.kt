package com.geekhaven.venuebookingsystem.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class AbsListAdapter<VB: ViewBinding, T>(
  private val itemList: ArrayList<T>,
  protected open val onClick: (id: String) -> Unit,
): RecyclerView.Adapter<AbsListAdapter<VB,T>.ViewHolder>(){

  inner class ViewHolder(val binding:VB):
    RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ViewHolder(inflateViewBinding(parent))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    renderItem(itemList[position], holder.binding)
    holder.itemView.setOnClickListener { onClick(getListItemId(position)) }
  }

  override fun getItemCount(): Int = itemList.size

  abstract fun inflateViewBinding(parent: ViewGroup): VB
  abstract fun renderItem(item: T, binding: VB)
  abstract fun getListItemId(position: Int): String

}
