package com.example.albertsome_task.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.flatMap
import androidx.paging.map
import com.example.albertsome_task.listener.RandomUserListener
import com.example.albertsome_task.model.User
import com.example.albertsome_task.model.UserResult
import com.example.albertsome_task.network.ApiService
import com.example.albertsome_task.paging.UserPagingSource
import com.example.albertsome_task.sealed.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPagerViewModel @Inject constructor(private val userListener: RandomUserListener) : ViewModel(){

    private var _userFlow:MutableStateFlow<PagingData<User>> = MutableStateFlow(PagingData.empty())
    val userFlow: StateFlow<PagingData<User>> = _userFlow

    private var _searchQuery = MutableStateFlow("")
    private val searchQuery: StateFlow<String> = _searchQuery

    private var _ageSort = MutableStateFlow(false)
    private val ageSort: StateFlow<Boolean> = _ageSort

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
                .combine(searchQuery){pagingData,query->
                    applyActions(pagingData,query)
                }.onEach {
                    _userFlow.value = it
                }.collect()
        }
    }

    private fun applyActions(
        pagingData: PagingData<User>,
        query: String
    ): PagingData<User> {
        return if(query.isNotEmpty()){
            pagingData.filter {
                it.name.first.contains(query, ignoreCase = true) ||
                        it.name.last.contains(query, ignoreCase = true)
            }
        }else{
            pagingData
        }
    }

    fun updateSearchQuery(query:String){
        _searchQuery.value = query
    }
}