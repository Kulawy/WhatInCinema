package com.gerapp.whatincinema.data.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class OfflineCacheInterceptor constructor(val context: Context) : Interceptor {

    override fun intercept(chain: Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        if (!isInternetAvailable(context)) {
            builder.cacheControl(CacheControl.FORCE_CACHE)
        }
        return chain.proceed(builder.build())
    }

    private fun isInternetAvailable(context: Context): Boolean {
        var isConnected = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected) {
            isConnected = true
        }
        return isConnected
    }
}
