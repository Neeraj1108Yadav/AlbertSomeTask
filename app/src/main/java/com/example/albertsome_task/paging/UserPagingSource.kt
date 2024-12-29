package com.example.albertsome_task.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.albertsome_task.model.User
import com.example.albertsome_task.network.ApiService
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val totalRequiredRecords:Int) : PagingSource<Int, User>(){

    private var fetchedRecords = 0
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
       return state.anchorPosition?.let {anchorPosition->
           state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
               ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
       }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1
        return try{

            if (fetchedRecords >= totalRequiredRecords) {
                fetchedRecords = 0
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            val response = apiService.getRandomUserPageWise(page,params.loadSize)
            if(response.isSuccessful){
                val userResult = response.body()
                val users = userResult?.results ?: emptyList()
                fetchedRecords += users.size

                LoadResult.Page(
                    data = users,
                    prevKey = if(page == 1) null else page - 1, //if first page, no previous page
                    nextKey = if(fetchedRecords >= totalRequiredRecords) null else page + 1 // If no user returned, end paging
                )

            }else{
                LoadResult.Error(Exception("API call failed"))
            }
        }catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }
}