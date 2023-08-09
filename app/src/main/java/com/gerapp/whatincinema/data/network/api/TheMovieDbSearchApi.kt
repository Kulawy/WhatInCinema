package com.gerapp.whatincinema.data.network.api

import com.gerapp.whatincinema.data.network.model.SearchMovieDto
import retrofit2.http.GET
import retrofit2.http.Query

private const val INCLUDE_ADULT = "true"
private const val LANGUAGE_CODE_US = "en-US"

interface TheMovieDbSearchApi {
    @GET("movie")
    suspend fun getSearchResults(
        @Query("query") query: String,
        @Query("include_adult") adult: String = INCLUDE_ADULT,
        @Query("language") language: String = LANGUAGE_CODE_US,
        @Query("page") page: Int,
    ): SearchMovieDto
}
