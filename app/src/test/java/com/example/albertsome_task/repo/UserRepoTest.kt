package com.example.albertsome_task.repo

import com.example.albertsome_task.Helper
import com.example.albertsome_task.network.ApiService
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
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

    private val gson = Gson()

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

        val json = gson.toJson(response.body())
        val resultResponse = JsonParser.parseString(json)
        val expectedResponse = JsonParser.parseString(mockData)

        //Assert
        assertEquals(expectedResponse.asJsonObject.get("results").asJsonArray.size(),2)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}