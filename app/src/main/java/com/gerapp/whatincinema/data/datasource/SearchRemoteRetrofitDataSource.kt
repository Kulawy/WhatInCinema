package com.gerapp.whatincinema.data.datasource

import com.gerapp.whatincinema.data.network.api.TheMovieDbSearchApi
import com.gerapp.whatincinema.domain.data.MovieSnap
import javax.inject.Inject

class SearchRemoteRetrofitDataSource @Inject constructor(
    private val searchMovieDbApi: TheMovieDbSearchApi,
) : SearchRemoteDataSource {
    override suspend fun searchMovie(query: String, page: Int): List<MovieSnap> =
        searchMovieDbApi.getSearchResults(
            query = query,
            page = page,
        ).results.map { dto -> dto.toDomain() }
}
