package com.james.weatherapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {
    fun isNetworkConnected(context: Context): Boolean {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as?
                ConnectivityManager)?.let { connectivityManager ->
            connectivityManager.
                getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
            }
        }
        return false
    }
}