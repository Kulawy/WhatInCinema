package com.gerapp.whatincinema.data.network.interceptor

import com.gerapp.whatincinema.data.network.CustomHeader.AUTHORIZATION
import com.gerapp.whatincinema.data.network.CustomHeader.BEARER
import com.gerapp.whatincinema.data.network.persistance.AuthPersistentStorage
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(var authPersistenceStorage: AuthPersistentStorage) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request().newBuilder()
                .addHeader(
                    AUTHORIZATION,
                    "$BEARER ${authPersistenceStorage.accessToken}",
                ).build(),
        )

//    val request = chain.request()
//    val builder = request.newBuilder()
//    if (request.header (HEADER_TOKEN_REQUESTED_KEY) != null)
//    {
//        builder.removeHeader(HEADER_ACCESS_TOKEN_REQUESTED_KEY)
//        builder.addHeader(AUTHORIZATION, $BEARER ${authPersistenceStorage.accessToken})
//    } else {
//        builder.removeHeader(HEADER_ACCESS_TOKEN_REQUESTED_KEY)
//
//    }
//
//    return chain.proceed(builder.build())
}
