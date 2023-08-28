package com.gerapp.whatincinema.data.repository

import com.gerapp.whatincinema.data.datasource.SearchRemoteDataSource
import com.gerapp.whatincinema.data.network.mapper.NetworkResponseMapper
import com.gerapp.whatincinema.domain.mapper.MovieSnapMapper
import com.gerapp.whatincinema.domain.model.MovieSnap
import com.gerapp.whatincinema.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val networkResponseMapper: NetworkResponseMapper,
    private val movieSnapMapper: MovieSnapMapper,
) : SearchRepository {
    override suspend fun searchMovie(query: String, page: Int): Result<Iterable<MovieSnap>> =
        networkResponseMapper.mapToResult(
            remoteDataSource.searchMovie(query, page),
            movieSnapMapper,
        )
}
