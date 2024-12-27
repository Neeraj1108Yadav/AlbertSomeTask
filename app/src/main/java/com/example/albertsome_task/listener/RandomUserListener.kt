package com.example.albertsome_task.listener

import com.example.albertsome_task.model.User
import com.example.albertsome_task.model.UserResult
import com.example.albertsome_task.sealed.NetworkResult

interface RandomUserListener {
    suspend fun getUser(): NetworkResult<UserResult>
}