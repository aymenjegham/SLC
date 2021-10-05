package com.aymen.slc.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aymen.slc.R
import com.aymen.slc.data.model.user.UsersType
import com.aymen.slc.databinding.ItemTypeBinding
import com.aymen.slc.global.helpers.DebugLog


class RoleViewHolder private constructor(
    private val binding: ItemTypeBinding,
    private val enableAction: (Int) -> Unit,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Int) {
        binding.type = when (UsersType.from(item)) {
            UsersType.HOTEL_ACCESS_CONTROL -> context.getString(R.string.hotel_access_control)
            UsersType.SUPER_ADMIN -> context.getString(R.string.super_admin)
            UsersType.SECRETARIAT -> context.getString(R.string.secretariat)
            UsersType.WORKSHOP_ACCESS_CONTROL -> context.getString(R.string.workshop_access_control)
            UsersType.EVENT_ACCESS_CONTROL -> context.getString(R.string.event_access_control)
            UsersType.RESTAURANT -> context.getString(R.string.restaurant)
            UsersType.CONFERENCE -> context.getString(R.string.conference)

            else -> "---------"

        }

        binding.icon = when (UsersType.from(item)) {
            UsersType.HOTEL_ACCESS_CONTROL -> context.getDrawable(R.drawable.ic_hotel)
            UsersType.SUPER_ADMIN -> context.getDrawable(R.drawable.ic_superadmin)
            UsersType.SECRETARIAT -> context.getDrawable(R.drawable.ic_secretary)
            UsersType.WORKSHOP_ACCESS_CONTROL -> context.getDrawable(R.drawable.ic_workshop)
            UsersType.EVENT_ACCESS_CONTROL -> context.getDrawable(R.drawable.ic_event)
            UsersType.RESTAURANT -> context.getDrawable(R.drawable.ic_restaurant)
            UsersType.CONFERENCE -> context.getDrawable(R.drawable.ic_conference)

            else -> context.getDrawable(R.drawable.ic_code_scanner_flash_on)
        }

        binding.item.setOnClickListener {
            enableAction(absoluteAdapterPosition)
        }


    }

    companion object {
        fun create(
            parent: ViewGroup,
            enableAction: (Int) -> Unit,
            context: Context
        ) =
            LayoutInflater.from(parent.context)
                .let { ItemTypeBinding.inflate(it, parent, false) }
                .let { RoleViewHolder(it, enableAction, context) }
    }
}