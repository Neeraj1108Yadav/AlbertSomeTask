package com.example.albertsome_task.repo

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.albertsome_task.listener.RandomUserListener
import com.example.albertsome_task.model.User
import com.example.albertsome_task.model.UserResult
import com.example.albertsome_task.network.ApiService
import com.example.albertsome_task.paging.UserPagingSource
import com.example.albertsome_task.sealed.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class UserRepo @Inject constructor(val apiService: ApiService) : RandomUserListener {

    override suspend fun getUser(): Flow<NetworkResult<UserResult>> {
        return flow{
            emit(NetworkResult.Loading)
            val response = apiService.getRandomUser()
            if(response.isSuccessful && response.body() != null){
                emit(NetworkResult.Success(response.body()!!))
            }else{
                emit(NetworkResult.Failure("Something went wrong"))
            }
        }.onStart {
            emit(NetworkResult.Loading)
        }.catch {execption->
            execption.message?.let { Log.e("App",it) }
            NetworkResult.Failure(execption.toString())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPagedUser(totalRecordsToFetch:Int): Flow<PagingData<User>> {
       return Pager(
           config = PagingConfig(
               pageSize = 10, //Show 10 records per page
               initialLoadSize = 10 //Fetch the required records
           ),
           pagingSourceFactory = { UserPagingSource(apiService,totalRecordsToFetch)}
       ).flow
    }
}