package com.gerapp.whatincinema.data.datasource

import com.gerapp.whatincinema.data.localstorage.FavouriteStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteHashSetDataSource @Inject constructor(
    private val storage: FavouriteStorage,
) : FavouriteLocalDataSource {

    override suspend fun getAllFavouriteMovieId(): List<Int> {
        return storage.getFavourites()
    }

    override suspend fun addFavouriteMovieId(movieId: Int) {
        storage.addFavourite(movieId)
    }

    override suspend fun deleteFavouriteMovieId(movieId: Int) {
        storage.deleteFavourite(movieId)
    }

    override suspend fun isMovieFavourite(movieId: Int): Boolean =
        storage.isFavourite(movieId)
}
