package com.gerapp.whatincinema.data.network.api

import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDbImageApi {
    @GET("original/{image_path}")
    suspend fun posterOriginalQuality(@Path("image_path") image_path: Int): ByteArray?

    @GET("w500/{image_path}")
    suspend fun posterMediumQuality(@Path("image_path") image_path: Int): ByteArray?
}
