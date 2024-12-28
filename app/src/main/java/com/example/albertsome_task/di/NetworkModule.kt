package com.example.albertsome_task.di

import android.content.Context
import com.example.albertsome_task.constant.AppConstant
import com.example.albertsome_task.network.ApiService
import com.example.albertsome_task.network.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created By Neeraj Yadav on 27/12/24
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitBuild(): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AppConstant.BASE_URL)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context): NetworkConnectionInterceptor{
        return NetworkConnectionInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideHttpBuilder(
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient.Builder{
        return OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor(loggingInterceptor)
    }

    @Singleton
    @Provides
    fun provideAppService(retrofitBuilder: Retrofit.Builder,
                          okHttpClient: OkHttpClient.Builder): ApiService {
        return retrofitBuilder
            .client(okHttpClient.build())
            .build()
            .create(ApiService::class.java)
    }
}