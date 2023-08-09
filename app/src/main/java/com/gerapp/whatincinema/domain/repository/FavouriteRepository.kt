package com.gerapp.whatincinema.domain.repository

interface FavouriteRepository {
    suspend fun addMovieToFavourites(movieId: Int)
    suspend fun removeMovieFormFavourites(movieId: Int)
    suspend fun checkIsMovieFavourite(movieId: Int): Boolean
    suspend fun getFavouriteMoviesIds(): List<Int>
}
