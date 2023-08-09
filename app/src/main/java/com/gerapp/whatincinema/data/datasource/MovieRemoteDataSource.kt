package com.gerapp.whatincinema.data.datasource

import com.gerapp.whatincinema.domain.data.MovieDetails
import com.gerapp.whatincinema.domain.data.MovieSnap

interface MovieRemoteDataSource {
    suspend fun getMovies(page: Int): List<MovieSnap>
    suspend fun getMovie(movieId: Int): MovieDetails?
}
