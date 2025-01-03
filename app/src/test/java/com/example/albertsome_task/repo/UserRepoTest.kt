package com.example.albertsome_task.repo

import com.example.albertsome_task.Helper
import com.example.albertsome_task.network.ApiService
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class UserRepoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun `test random user response`() = runTest{
        //Arrange
        val mockData = Helper.getJsonFile("user.json")
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(mockData)
        mockWebServer.enqueue(mockResponse)

        //Act
        val response = apiService.getRandomUserPageWise()

        //Assert
        assertNotNull(response.body())
        val users = response.body()?.results
        assertEquals(2,users?.size)

        val firstUser = users?.get(0)
        assertEquals("male", firstUser?.gender)
        assertEquals("Nicholas", firstUser?.name?.first)
        assertEquals("Albert Road", firstUser?.location?.street?.name)
    }

    @Test
    fun `test error response`() = runTest {
        // Arrange
        val mockResponse = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = apiService.getRandomUserPageWise()

        // Assert
        assertFalse(response.isSuccessful)
        assertEquals(404, response.code())
    }

    @Test
    fun `test empty response`() = runTest {
        // Arrange
        val mockData = Helper.getJsonFile("empty_result.json")
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(mockData)
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = apiService.getRandomUserPageWise()

        // Assert
        val users = response.body()?.results
        assertNotNull(users)
        assertTrue(users.isNullOrEmpty())
    }

    @Test
    fun `test query parameters`() = runTest {
        // Arrange
        val mockData = Helper.getJsonFile("user.json")
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(mockData)

        mockWebServer.enqueue(mockResponse)

        // Act
        apiService.getRandomUserPageWise()

        // Assert
        val recordedRequest = mockWebServer.takeRequest()
        assertTrue(recordedRequest.path?.contains("page=1") == true)
        assertTrue(recordedRequest.path?.contains("results=10") == true)
    }


    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}