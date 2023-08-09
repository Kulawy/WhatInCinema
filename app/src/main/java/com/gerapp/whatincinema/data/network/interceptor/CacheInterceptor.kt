package com.gerapp.whatincinema.data.network.interceptor

import com.gerapp.whatincinema.data.DataConstants.CACHE_CONTROL_HEADER
import com.gerapp.whatincinema.data.DataConstants.CACHE_DAYS_AMOUNT
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(CACHE_DAYS_AMOUNT, TimeUnit.DAYS)
            .build()
        return response.newBuilder()
            .header(CACHE_CONTROL_HEADER, cacheControl.toString())
            .build()
    }
}
