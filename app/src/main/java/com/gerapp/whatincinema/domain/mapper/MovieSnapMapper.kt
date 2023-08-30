package com.gerapp.whatincinema.domain.mapper

import com.gerapp.whatincinema.data.network.model.MovieSnapDto
import com.gerapp.whatincinema.domain.model.MovieSnap
import javax.inject.Inject

class MovieSnapMapper @Inject constructor() :
    DtoResponseMapper<MovieSnapDto, MovieSnap>,
    DtoResponseIterableMapper<MovieSnapDto, MovieSnap> {

    override fun mapIterableToDomain(dto: Iterable<MovieSnapDto>): List<MovieSnap> =
        dto.map { movieSnapDto ->
            toDomain(movieSnapDto)
        }

    override fun toDomain(dto: MovieSnapDto): MovieSnap =
        MovieSnap(
            dto.id,
            dto.adult ?: false,
            posterPath = dto.posterPath!!,
            releaseDate = dto.releaseDate,
            titleLng = dto.title,
            originalTitle = dto.originalTitle,
        )
}
