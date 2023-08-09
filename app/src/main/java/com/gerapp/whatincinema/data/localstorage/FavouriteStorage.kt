package com.gerapp.whatincinema.data.localstorage

interface FavouriteStorage {
    suspend fun addFavourite(movieId: Int): Boolean
    suspend fun deleteFavourite(movieId: Int): Boolean
    suspend fun getFavourites(): List<Int>
    suspend fun isFavourite(movieId: Int): Boolean
}
