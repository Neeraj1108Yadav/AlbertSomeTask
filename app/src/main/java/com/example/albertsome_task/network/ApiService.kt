package com.example.albertsome_task.network

import com.example.albertsome_task.model.UserResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun getRandomUser(
        @Query("results") result:Int = 5,
        @Query("inc") inc: String = "name,gender,location,email,dob,phone,cell,picture"
    ) : Response<UserResult>

    @GET("api/")
    suspend fun getRandomUserPageWise(
        @Query("page") page:Int = 1,
        @Query("results") result:Int = 10,
        @Query("inc") inc: String = "name,gender,location,email,dob,phone,cell,picture"
    ) : Response<UserResult>
}