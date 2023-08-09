package com.gerapp.whatincinema.data.repository

import com.gerapp.whatincinema.data.datasource.SearchRemoteDataSource
import com.gerapp.whatincinema.domain.data.MovieSnap
import com.gerapp.whatincinema.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
) : SearchRepository {
    override suspend fun searchMovie(query: String, page: Int): List<MovieSnap> =
        remoteDataSource.searchMovie(query, page)
}
