package com.example.albertsome_task.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.albertsome_task.model.User
import com.example.albertsome_task.network.ApiService
import javax.inject.Inject

class UserPagingSource @Inject constructor(private val apiService: ApiService) : PagingSource<Int, User>(){

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
       return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1
        return try{
            val response = apiService.getRandomUserPageWise(page,params.loadSize)
            if(response.isSuccessful){
                val userResult = response.body()
                val users = userResult?.results ?: emptyList()

                LoadResult.Page(
                    data = users,
                    prevKey = if(page == 1) null else page - 1, //if first page, no previous page
                    nextKey = if(users.isEmpty()) null else page + 1 // If no user returned, end paging
                )
            }else{
                LoadResult.Error(Exception("API call failed"))
            }
        }catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }
}