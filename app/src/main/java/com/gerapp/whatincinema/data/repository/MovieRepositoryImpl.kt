package com.gerapp.whatincinema.data.repository

import com.gerapp.whatincinema.data.datasource.MovieRemoteDataSource
import com.gerapp.whatincinema.data.network.mapper.NetworkResponseMapper
import com.gerapp.whatincinema.domain.mapper.MovieDetailsMapper
import com.gerapp.whatincinema.domain.mapper.MovieSnapMapper
import com.gerapp.whatincinema.domain.model.MovieDetails
import com.gerapp.whatincinema.domain.model.MovieSnap
import com.gerapp.whatincinema.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val networkResponseMapper: NetworkResponseMapper,
    private val movieSnapMapper: MovieSnapMapper,
    private val movieDetailsMapper: MovieDetailsMapper,
) : MovieRepository {
    override suspend fun getMovies(page: Int): Result<Iterable<MovieSnap>> =
        networkResponseMapper.mapToResult(remoteDataSource.getMovies(page), movieSnapMapper)

    override suspend fun getMovie(id: Int): Result<MovieDetails> =
        networkResponseMapper.mapToResult(remoteDataSource.getMovie(id), movieDetailsMapper)
}
