package com.gerapp.whatincinema.domain.repository

import com.gerapp.whatincinema.domain.data.MovieDetails
import com.gerapp.whatincinema.domain.data.MovieSnap

interface MovieRepository {
    suspend fun getMovies(page: Int): List<MovieSnap>
    suspend fun getMovie(id: Int): MovieDetails?
}
