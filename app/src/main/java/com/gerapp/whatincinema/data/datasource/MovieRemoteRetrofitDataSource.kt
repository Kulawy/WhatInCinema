package com.gerapp.whatincinema.data.datasource

import com.gerapp.whatincinema.data.mapper.ResponseApiWrapper
import com.gerapp.whatincinema.data.network.api.TheMovieDbApi
import com.gerapp.whatincinema.data.network.model.MovieSnapDto
import com.gerapp.whatincinema.data.network.model.NetworkResponse
import javax.inject.Inject

class MovieRemoteRetrofitDataSource @Inject constructor(
    private val movieDbApi: TheMovieDbApi,
    private val responseApiWrapper: ResponseApiWrapper,
) : MovieRemoteDataSource {
    override suspend fun getMovies(page: Int): NetworkResponse<List<MovieSnapDto>> =
        try {
            responseApiWrapper.wrapResponse(
                movieDbApi.getNowPlaying(page).results
                    .filter { it.title != null || it.originalTitle != null },
            )
        } catch (e: Exception) {
            responseApiWrapper.wrapError(e)
        }

    override suspend fun getMovie(movieId: Int) =
        try {
            responseApiWrapper.wrapResponse(movieDbApi.movieDetails(movieId))
        } catch (e: Exception) {
            responseApiWrapper.wrapError(e)
        }
}
