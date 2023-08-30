package com.gerapp.whatincinema.data.datasource

import com.gerapp.whatincinema.data.network.model.MovieDetailsDto
import com.gerapp.whatincinema.data.network.model.MovieSnapDto
import com.gerapp.whatincinema.data.network.model.NetworkResponse

interface MovieRemoteDataSource {
    suspend fun getMovies(page: Int): NetworkResponse<List<MovieSnapDto>>
    suspend fun getMovie(movieId: Int): NetworkResponse<MovieDetailsDto>
}
