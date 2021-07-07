package com.example.userslistapp.networking

import android.net.ConnectivityManager
import android.net.NetworkInfo

interface NetworkService {
    fun isConnected(): Boolean
}

class NetworkServiceImpl(private val connMgr: ConnectivityManager): NetworkService {
    override fun isConnected(): Boolean {
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}