package com.gerapp.whatincinema.data.datasource

import com.gerapp.whatincinema.domain.data.MovieSnap

interface SearchRemoteDataSource {
    suspend fun searchMovie(query: String, page: Int): List<MovieSnap>
}
