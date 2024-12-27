package com.example.albertsome_task.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.albertsome_task.listener.RandomUserListener
import com.example.albertsome_task.model.UserResult
import com.example.albertsome_task.sealed.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userListener: RandomUserListener) : ViewModel() {

    private var _userData: MutableLiveData<NetworkResult<UserResult>> = MutableLiveData()
    val userData: LiveData<NetworkResult<UserResult>> = _userData

    fun getUser(){
        viewModelScope.launch{
            _userData.value = userListener.getUser()
        }
    }
}