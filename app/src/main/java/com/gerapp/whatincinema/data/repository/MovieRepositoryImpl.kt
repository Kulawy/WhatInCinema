package com.gerapp.whatincinema.data.repository

import com.gerapp.whatincinema.data.datasource.MovieRemoteDataSource
import com.gerapp.whatincinema.domain.data.MovieDetails
import com.gerapp.whatincinema.domain.data.MovieSnap
import com.gerapp.whatincinema.domain.repository.MovieRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
) : MovieRepository {
    override suspend fun getMovies(page: Int): List<MovieSnap> =
        remoteDataSource.getMovies(page)

    override suspend fun getMovie(id: Int): MovieDetails {
        val movie = remoteDataSource.getMovie(id)
        Timber.d("movie: ${movie?.title}")
        return movie!!
    }
}
