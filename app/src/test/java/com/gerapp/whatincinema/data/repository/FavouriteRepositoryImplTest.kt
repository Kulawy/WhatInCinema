package com.gerapp.whatincinema.data.repository

import com.gerapp.whatincinema.data.datasource.FavouriteLocalDataSource
import com.gerapp.whatincinema.util.DispatcherSetupExtension
import com.gerapp.whatincinema.util.MockkInjectorExtension
import com.gerapp.whatincinema.util.coVerifyOnce
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockkInjectorExtension::class, DispatcherSetupExtension::class)
internal class FavouriteRepositoryTest {

    @RelaxedMockK
    lateinit var favouriteLocalDataSource: FavouriteLocalDataSource

    @InjectMockKs
    lateinit var sut: FavouriteRepositoryImpl

    @Test
    fun `Check that addMovieToFavourite, adds movie to favourite locally`() = runTest {
        val testMovieId = 123
        sut.addMovieToFavourites(testMovieId)

        coVerifyOnce { favouriteLocalDataSource.addFavouriteMovieId(testMovieId) }
        confirmVerified(favouriteLocalDataSource)
    }

    @Test
    fun `Check that removeMovieFormFavourites, removes movie from favourite locally`() = runTest {
        val testMovieId = 123
        sut.removeMovieFormFavourites(testMovieId)

        coVerifyOnce { favouriteLocalDataSource.deleteFavouriteMovieId(testMovieId) }
        confirmVerified(favouriteLocalDataSource)
    }

    @Test
    fun `Check that checkIsMovieFavourite, check if movie is favourite locally`() = runTest {
        val testMovieId = 123
        sut.checkIsMovieFavourite(testMovieId)

        coVerifyOnce { favouriteLocalDataSource.isMovieFavourite(testMovieId) }
        confirmVerified(favouriteLocalDataSource)
    }

    @Test
    fun `Check that getFavouriteMoviesIds, get all favourite movies Ids from local`() = runTest {
        sut.getFavouriteMoviesIds()

        coVerifyOnce { favouriteLocalDataSource.getAllFavouriteMovieId() }
        confirmVerified(favouriteLocalDataSource)
    }
}
