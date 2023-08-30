package com.gerapp.whatincinema.domain.model

/*
As I want to display as much movies as it's possible I set nullable types
As I use Pagination 3 library and don't have exact model for presentation I handle null types in mappers
 */
data class MovieSnap(
    val id: Int,
    val adult: Boolean,
    val posterPath: String,
    val releaseDate: String?,
    val titleLng: String?,
    val originalTitle: String?,
    val isFavourite: Boolean = false,
) {
    val title
        get() = titleLng ?: originalTitle!!
// if there is no titleLng there must be originalTitle, as data is filtered for it
}
