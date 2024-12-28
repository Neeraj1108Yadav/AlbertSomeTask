package com.example.albertsome_task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.albertsome_task.databinding.ListUserBinding
import com.example.albertsome_task.model.User
import com.example.albertsome_task.ui.home.UserDiffCallback

class UserAdapter(private val onClickListener:(User) -> Unit) : ListAdapter<User,UserAdapter.ViewHolder>(UserDiffCallback()){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListUserBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val user = getItem(position)
        holder.binding.user = user
        holder.binding.root.setOnClickListener{
            onClickListener.invoke(getItem(holder.adapterPosition))
        }
        holder.binding.executePendingBindings()
    }

    inner class ViewHolder(val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root)
}