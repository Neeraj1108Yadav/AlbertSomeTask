package com.example.albertsome_task.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.albertsome_task.listener.RandomUserListener
import com.example.albertsome_task.model.User
import com.example.albertsome_task.model.UserResult
import com.example.albertsome_task.network.ApiService
import com.example.albertsome_task.paging.UserPagingSource
import com.example.albertsome_task.sealed.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPagerViewModel @Inject constructor(private val userListener: RandomUserListener) : ViewModel(){

    private var _userFlow:MutableStateFlow<PagingData<User>> = MutableStateFlow(PagingData.empty())
    val userFlow: StateFlow<PagingData<User>> = _userFlow

    private var totalRequiredRecords = 10
    private var fetchedRecords = 0

    fun fetchUser(recordsToFetch:Int = 10){
        totalRequiredRecords = recordsToFetch
        fetchedRecords = 0
        loadUsers()
    }

    private fun loadUsers(){
        viewModelScope.launch {
            userListener.getPagedUser(totalRequiredRecords)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _userFlow.value = pagingData
                }
        }
    }
}