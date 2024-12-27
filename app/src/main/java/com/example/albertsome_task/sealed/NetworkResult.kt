package com.example.albertsome_task.sealed


sealed class NetworkResult<out T>{
    object Loading : NetworkResult<Nothing>()
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Failure(val message:String) : NetworkResult<Nothing>()
}