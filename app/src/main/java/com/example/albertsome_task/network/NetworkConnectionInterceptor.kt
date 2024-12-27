package com.example.albertsome_task.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isInternetConnectionAvailable()){
            throw NetworkConnectionException("No Internet Connection")
        }

        val request = chain.request()
        val response = chain.proceed(request)
        return response
    }

    private fun isInternetConnectionAvailable() : Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network as? Network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}