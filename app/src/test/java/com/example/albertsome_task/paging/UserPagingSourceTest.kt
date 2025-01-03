package com.example.albertsome_task.paging

import androidx.paging.PagingSource
import com.example.albertsome_task.Helper
import com.example.albertsome_task.model.User
import com.example.albertsome_task.model.UserResult
import com.example.albertsome_task.network.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
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
        val mockData = Helper.getJsonFile("small_data.json")
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

    @Test
    fun `test paging source with MockWebServer and UserResult`() = runTest {
        // Arrange
        val mockResponse = Helper.getJsonFile("very_large_data.json")
        val userResult: UserResult = gson.fromJson(mockResponse, object : TypeToken<UserResult>() {}.type)
        val allUsers: List<User> = userResult.results
        // Enqueue paginated responses for each page
        val pageSize = 10
        for (i in 0 until allUsers.size step pageSize) {
            val pageData = allUsers.subList(i, minOf(i + pageSize, allUsers.size))
            val userResult = UserResult(results = pageData) // Wrap in `UserResult`
            val pageJson = Gson().toJson(userResult) // Convert to JSON
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(pageJson)
            )
        }

        val pagingSource = UserPagingSource(apiService,100)

        // Act and Assert: Iterate through pages
        var currentPage = 1
        val loadedItems = mutableListOf<User>()

        while (true) {
            val loadResult = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = currentPage,
                    loadSize = pageSize,
                    placeholdersEnabled = false
                )
            )

            assertTrue(loadResult is PagingSource.LoadResult.Page)
            val page = loadResult as PagingSource.LoadResult.Page

            // Verify the current page data
            val expectedData = allUsers.subList((currentPage - 1) * pageSize, minOf(currentPage * pageSize, allUsers.size))
            assertEquals(expectedData.size, page.data.size)
            assertEquals(expectedData, page.data)

            // Collect loaded items
            loadedItems.addAll(page.data)

            // Determine if there's a next page
            if (page.nextKey == null) break
            currentPage = page.nextKey!!
        }

        // Verify all 100 items were loaded
        assertEquals(100, loadedItems.size)
        assertEquals(allUsers, loadedItems)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}