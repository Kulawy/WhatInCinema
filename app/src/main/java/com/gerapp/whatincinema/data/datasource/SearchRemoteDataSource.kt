package com.gerapp.whatincinema.data.datasource

import com.gerapp.whatincinema.data.network.model.MovieSnapDto
import com.gerapp.whatincinema.data.network.model.NetworkResponse

interface SearchRemoteDataSource {
    suspend fun searchMovie(query: String, page: Int): NetworkResponse<List<MovieSnapDto>>
}
