package com.example.albertsome_task.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.albertsome_task.model.User

class UserDetailViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun setUser(user: User) {
        _user.value = user
    }
}