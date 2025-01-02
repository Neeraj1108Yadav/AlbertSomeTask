package com.example.albertsome_task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.albertsome_task.adapter.UserPagingDataAdapter.ViewHolder
import com.example.albertsome_task.databinding.ListUserBinding
import com.example.albertsome_task.model.User

class UserPagingDataAdapter(
    private val onClickListener:(User) -> Unit,
    private val searchView:(Boolean)-> Unit) : PagingDataAdapter<User,ViewHolder>(User.DIFF_CALLBACK) {

    private var enableSearchView:Boolean = false

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
        user?.let {
            if(!enableSearchView){
                enableSearchView = true
                searchView.invoke(true)
            }
            holder.binding.user = it
        }

        holder.binding.root.setOnClickListener{
            user?.let {item->
                onClickListener.invoke(item)
            }
        }

        holder.binding.executePendingBindings()
    }

    inner class ViewHolder(val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root)
}