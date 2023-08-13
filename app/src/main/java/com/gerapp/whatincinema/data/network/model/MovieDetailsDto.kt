package com.gerapp.whatincinema.data.network.model

import com.gerapp.whatincinema.domain.data.MovieDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(

    @SerialName("id") val id: Int = -1,
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
//    @SerialName("belongs_to_collection") val belongsToCollection: BelongsToCollection? = BelongsToCollection(),
    @SerialName("budget") val budget: Int? = null,
//    @SerialName("genres") val genres: ArrayList<Genres> = arrayListOf(),
    @SerialName("homepage") val homepage: String? = null,
    @SerialName("imdb_id") val imdbId: String? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("poster_path") val posterPath: String? = null,
//    @SerialName("production_companies") val productionCompanies: ArrayList<ProductionCompanies> = arrayListOf(),
//    @SerialName("production_countries") val productionCountries: ArrayList<ProductionCountries> = arrayListOf(),
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("revenue") val revenue: Int? = null,
    @SerialName("runtime") val runtime: Int? = null,
//    @SerialName("spoken_languages") val spokenLanguages: ArrayList<SpokenLanguages> = arrayListOf(),
    @SerialName("status") val status: String? = null,
    @SerialName("tagline") val tagline: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("video") val video: Boolean? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
) : DtoModel {
    override fun toDomain(): MovieDetails =
        MovieDetails(
            id,
            adult,
            backdropPath,
            budget,
            homepage,
            imdbId,
            originalLanguage,
            originalTitle,
            overview,
            popularity,
            posterPath,
            releaseDate,
            revenue,
            runtime,
            status,
            tagline,
            title,
            video,
            voteAverage?.toFloat(),
            voteCount,
        )
}
