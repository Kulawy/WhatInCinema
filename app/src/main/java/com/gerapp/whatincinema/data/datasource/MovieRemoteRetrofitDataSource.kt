package com.gerapp.whatincinema.data.datasource

import com.gerapp.whatincinema.data.network.api.TheMovieDbApi
import com.gerapp.whatincinema.domain.data.MovieDetails
import com.gerapp.whatincinema.domain.data.MovieSnap
import javax.inject.Inject

class MovieRemoteRetrofitDataSource @Inject constructor(
    private val movieDbApi: TheMovieDbApi,
) : MovieRemoteDataSource {
    override suspend fun getMovies(page: Int): List<MovieSnap> =
        movieDbApi.getNowPlaying(page).results.map { dto -> dto.toDomain() }

    override suspend fun getMovie(movieId: Int): MovieDetails? =
        movieDbApi.movieDetails(movieId)?.toDomain()
}
