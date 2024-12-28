package com.example.albertsome_task.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.example.albertsome_task.model.User

class UserDiffCallback: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean {
        // Compare unique IDs or reference equality
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean {
        // Compare content equality
        return oldItem == newItem
    }
}