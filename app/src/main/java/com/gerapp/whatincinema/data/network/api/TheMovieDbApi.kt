package com.gerapp.whatincinema.data.network.api

import com.gerapp.whatincinema.data.network.model.MovieDetailsDto
import com.gerapp.whatincinema.data.network.model.NowPlayingResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {
    @GET("now_playing")
    suspend fun getNowPlaying(@Query("page") page: Int): NowPlayingResponseDto

    @GET("{movie_id}")
    suspend fun movieDetails(@Path("movie_id") movieId: Int): MovieDetailsDto
}
