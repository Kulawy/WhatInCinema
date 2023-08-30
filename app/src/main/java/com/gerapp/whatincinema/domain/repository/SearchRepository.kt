package com.gerapp.whatincinema.domain.repository

import com.gerapp.whatincinema.domain.model.MovieSnap

interface SearchRepository {
    suspend fun searchMovie(query: String, page: Int): Result<Iterable<MovieSnap>>
}
