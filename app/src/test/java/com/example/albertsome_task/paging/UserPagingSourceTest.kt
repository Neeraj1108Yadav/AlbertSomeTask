package com.example.albertsome_task.paging

import androidx.paging.PagingSource
import com.example.albertsome_task.Helper
import com.example.albertsome_task.model.UserResult
import com.example.albertsome_task.network.ApiService
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class UserPagingSourceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var mockWebServer: MockWebServer

    private val gson = Gson()

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun `test paging source load success`() = runTest {
        // Arrange
        //val mockData: UserResult = gson.fromJson(Helper.getJsonFile("small_data.json"), UserResult::class.java)
        val mockData = Helper.getJsonFile("small_data.json")
        //coEvery { apiService.getRandomUserPageWise() } returns Response.success(mockData)
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(mockData)
        mockWebServer.enqueue(mockResponse)

        val pagingSource = UserPagingSource(apiService,10)

        // Act
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Assert
        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(10, page.data.size)
    }
}