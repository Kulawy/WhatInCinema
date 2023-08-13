package com.gerapp.whatincinema.domain.data

import com.gerapp.whatincinema.data.network.model.DtoModel
import com.gerapp.whatincinema.data.network.model.MovieSnapDto

data class MovieSnap(
    val id: Int = -1,
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val genreIds: ArrayList<Int> = arrayListOf(),
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val isFavourite: Boolean = false,
) : DomainModel {
    override fun toDto(): DtoModel = MovieSnapDto(
        id,
        adult,
        backdropPath,
        genreIds,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount,
    )
}
