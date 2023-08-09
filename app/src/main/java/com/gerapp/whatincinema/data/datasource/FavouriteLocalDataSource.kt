package com.gerapp.whatincinema.data.datasource

interface FavouriteLocalDataSource {
    suspend fun getAllFavouriteMovieId(): List<Int>
    suspend fun addFavouriteMovieId(movieId: Int)
    suspend fun deleteFavouriteMovieId(movieId: Int)
    suspend fun isMovieFavourite(movieId: Int): Boolean
}
