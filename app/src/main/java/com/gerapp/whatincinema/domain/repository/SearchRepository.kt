package com.gerapp.whatincinema.domain.repository

import com.gerapp.whatincinema.domain.data.MovieSnap

interface SearchRepository {
    suspend fun searchMovie(query: String, page: Int): List<MovieSnap>
}
