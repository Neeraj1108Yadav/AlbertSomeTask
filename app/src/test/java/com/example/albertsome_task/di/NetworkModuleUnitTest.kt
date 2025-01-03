package com.example.albertsome_task.di

import com.example.albertsome_task.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class NetworkModuleUnitTest {

    @Singleton
    @Provides
    fun provideRetrofit(mockWebServer: MockWebServer): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService{
        return retrofit.build().create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMockWebserver(): MockWebServer{
        return MockWebServer()
    }
}