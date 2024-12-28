package com.example.albertsome_task.repo

import android.util.Log
import com.example.albertsome_task.listener.RandomUserListener
import com.example.albertsome_task.model.User
import com.example.albertsome_task.model.UserResult
import com.example.albertsome_task.network.ApiService
import com.example.albertsome_task.sealed.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepo @Inject constructor(val apiService: ApiService) : RandomUserListener {

    override suspend fun getUser(): NetworkResult<UserResult> {
        return try{
            withContext(Dispatchers.IO){
                val response = apiService.getRandomUser()
                if(response.isSuccessful && response.body() != null){
                    NetworkResult.Success(response.body()!!)
                }else{
                    NetworkResult.Failure("Something went wrong")
                }
            }
        }catch (e: Exception){
            e.message?.let { Log.e("App",it) }
            NetworkResult.Failure(e.toString())
        }
    }

}