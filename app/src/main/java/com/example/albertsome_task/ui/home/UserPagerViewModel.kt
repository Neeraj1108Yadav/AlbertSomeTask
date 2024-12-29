package com.example.albertsome_task.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.albertsome_task.model.User
import com.example.albertsome_task.network.ApiService
import com.example.albertsome_task.paging.UserPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserPagerViewModel @Inject constructor(private val apiService: ApiService) : ViewModel(){

    val flow: Flow<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { UserPagingSource(apiService) }
    ).flow.cachedIn(viewModelScope)

}