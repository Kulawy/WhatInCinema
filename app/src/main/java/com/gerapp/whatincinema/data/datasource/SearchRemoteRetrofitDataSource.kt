package com.gerapp.whatincinema.data.datasource

import com.gerapp.whatincinema.data.mapper.ResponseApiWrapper
import com.gerapp.whatincinema.data.network.api.TheMovieDbSearchApi
import com.gerapp.whatincinema.data.network.model.MovieSnapDto
import com.gerapp.whatincinema.data.network.model.NetworkResponse
import javax.inject.Inject

class SearchRemoteRetrofitDataSource @Inject constructor(
    private val searchMovieDbApi: TheMovieDbSearchApi,
    private val responseApiWrapper: ResponseApiWrapper,
) : SearchRemoteDataSource {
    override suspend fun searchMovie(
        query: String,
        page: Int,
    ): NetworkResponse<List<MovieSnapDto>> =
        try {
            responseApiWrapper.wrapResponse(
                searchMovieDbApi.getSearchResults(
                    query = query,
                    page = page,
                ).results,
            )
        } catch (e: Exception) {
            responseApiWrapper.wrapError(e)
        }
}
