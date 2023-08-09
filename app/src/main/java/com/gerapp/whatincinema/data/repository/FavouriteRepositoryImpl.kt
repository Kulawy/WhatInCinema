package com.gerapp.whatincinema.data.repository

import com.gerapp.whatincinema.data.datasource.FavouriteLocalDataSource
import com.gerapp.whatincinema.domain.repository.FavouriteRepository
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val favouriteDataSource: FavouriteLocalDataSource,
) : FavouriteRepository {
    override suspend fun addMovieToFavourites(movieId: Int) =
        favouriteDataSource.addFavouriteMovieId(movieId)

    override suspend fun removeMovieFormFavourites(movieId: Int) =
        favouriteDataSource.deleteFavouriteMovieId(movieId)

    override suspend fun checkIsMovieFavourite(movieId: Int): Boolean =
        favouriteDataSource.isMovieFavourite(movieId)

    override suspend fun getFavouriteMoviesIds(): List<Int> =
        favouriteDataSource.getAllFavouriteMovieId()
}
