package com.example.albertsome_task.listener

import androidx.paging.PagingData
import com.example.albertsome_task.model.User
import com.example.albertsome_task.model.UserResult
import com.example.albertsome_task.sealed.NetworkResult
import kotlinx.coroutines.flow.Flow

interface RandomUserListener {
    suspend fun getUser(): Flow<NetworkResult<UserResult>>
    suspend fun getPagedUser(totalRecordsToFetch:Int): Flow<PagingData<User>>
}