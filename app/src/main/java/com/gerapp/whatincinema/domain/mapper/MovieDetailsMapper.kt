package com.gerapp.whatincinema.domain.mapper

import com.gerapp.whatincinema.data.network.model.MovieDetailsDto
import com.gerapp.whatincinema.domain.model.MovieDetails
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor() :
    DtoResponseMapper<MovieDetailsDto, MovieDetails> {

    override fun toDomain(dto: MovieDetailsDto) =
        MovieDetails(
            dto.id,
            dto.adult,
            dto.backdropPath,
            dto.budget,
            dto.homepage,
            dto.imdbId,
            dto.originalLanguage,
            dto.originalTitle,
            dto.overview,
            dto.popularity,
            dto.posterPath,
            dto.releaseDate,
            dto.revenue,
            dto.runtime,
            dto.status,
            dto.tagline,
            dto.title,
            dto.video,
            dto.voteAverage?.toFloat(),
            dto.voteCount,
        )
}
