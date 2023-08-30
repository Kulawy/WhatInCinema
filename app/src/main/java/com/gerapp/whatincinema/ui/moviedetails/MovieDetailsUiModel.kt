package com.gerapp.whatincinema.ui.moviedetails

import com.gerapp.whatincinema.domain.model.MovieDetails

data class MovieDetailsUiModel(
    val movieId: Int,
    val title: String,
    val releaseDate: String?,
    val overview: String?,
    val posterPath: String,
    val voteAverage: Float?,
) {
    constructor(domainModel: MovieDetails) :
        this(
            movieId = domainModel.id,
            title = domainModel.title ?: domainModel.originalTitle!!,
            releaseDate = domainModel.releaseDate,
            overview = domainModel.overview,
            posterPath = domainModel.posterPath,
            voteAverage = domainModel.voteAverage,
        )
}
