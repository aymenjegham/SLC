package com.aymen.slc.ui.main.home.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class RoleAdapter(
    private val enableAction: (Int) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<RoleViewHolder>() {

    private val types = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RoleViewHolder.create(parent, enableAction,context)


    override fun onBindViewHolder(holder: RoleViewHolder, position: Int) {
        holder.bind(types[position])
    }

    override fun getItemCount() =
        types.size


    fun submitList(newTypes: List<Int>) {
        DiffCallback(types, newTypes)
            .let {
                DiffUtil.calculateDiff(it)
            }
            .also {
                types.clear()
                types.addAll(newTypes)
                it.dispatchUpdatesTo(this)
            }

    }

    private class DiffCallback(
        private val old: List<Int>,
        private val new: List<Int>

    ) : DiffUtil.Callback() {

        override fun getOldListSize() = old.size

        override fun getNewListSize() = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            (old[oldItemPosition] == new[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            old[oldItemPosition] == new[newItemPosition]
    }

}