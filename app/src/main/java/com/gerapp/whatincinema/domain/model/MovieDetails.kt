package com.gerapp.whatincinema.domain.model

data class MovieDetails(
    val id: Int,
    val adult: Boolean?,
    val backdropPath: String?,
    val budget: Int?,
    val homepage: String?,
    val imdbId: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String,
    val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Float?,
    val voteCount: Int?,
)
