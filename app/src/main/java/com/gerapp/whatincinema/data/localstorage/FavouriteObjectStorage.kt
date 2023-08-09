package com.gerapp.whatincinema.data.localstorage

import timber.log.Timber

object FavouriteObjectStorage : FavouriteStorage {

    /***
     Fast Solution. Pros Why:
     1. Favourites are stored as collection of Ids - Integers.  Using Room here would be like aiming to fly from cannon.
     2. It's need to be Set as it's no matter about sort and it shouldn't have duplications.
     3. DataStore here don't fit as it's might be a big collection and we may want to change one element
     ***/

    private val favouriteMoviesIds = mutableSetOf<Int>()

    override suspend fun addFavourite(movieId: Int): Boolean {
        Timber.d("ADD INVOKE with element: $movieId")
        return favouriteMoviesIds.add(movieId)
    }

    override suspend fun deleteFavourite(movieId: Int) =
        favouriteMoviesIds.remove(element = movieId)

    override suspend fun getFavourites() = favouriteMoviesIds.toList()

    override suspend fun isFavourite(movieId: Int) = favouriteMoviesIds.contains(movieId)
}
