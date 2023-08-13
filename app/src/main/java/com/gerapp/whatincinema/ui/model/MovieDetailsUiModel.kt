package com.gerapp.whatincinema.ui.model

data class MovieDetailsUiModel(
    val movieId: Int,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Float,
)
