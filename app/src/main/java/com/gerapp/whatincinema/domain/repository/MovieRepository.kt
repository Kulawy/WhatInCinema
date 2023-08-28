package com.gerapp.whatincinema.domain.repository

import com.gerapp.whatincinema.domain.model.MovieDetails
import com.gerapp.whatincinema.domain.model.MovieSnap

interface MovieRepository {
    suspend fun getMovies(page: Int): Result<Iterable<MovieSnap>>
    suspend fun getMovie(id: Int): Result<MovieDetails>
}
