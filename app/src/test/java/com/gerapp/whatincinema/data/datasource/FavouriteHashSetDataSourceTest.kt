package com.gerapp.whatincinema.data.datasource

import com.gerapp.whatincinema.data.localstorage.FavouriteStorage
import com.gerapp.whatincinema.util.MockkInjectorExtension
import com.gerapp.whatincinema.util.coVerifyOnce
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockkInjectorExtension::class)
internal class FavouriteHashSetDataSourceTest {

    @RelaxedMockK
    lateinit var storage: FavouriteStorage

    @InjectMockKs
    lateinit var sut: FavouriteHashSetDataSource

    @org.junit.jupiter.api.Test
    fun `Check that addFavouriteMovieId, adds movie to favourite`() = runTest {
        val testMovieId = 123
        sut.addFavouriteMovieId(testMovieId)

        coVerifyOnce { storage.addFavourite(testMovieId) }
        confirmVerified(storage)
    }

    @org.junit.jupiter.api.Test
    fun `Check that deleteFavouriteMovieId, removes movie from favourite`() = runTest {
        val testMovieId = 123
        sut.deleteFavouriteMovieId(testMovieId)

        coVerifyOnce { storage.deleteFavourite(testMovieId) }
        confirmVerified(storage)
    }

    @org.junit.jupiter.api.Test
    fun `Check that isMovieFavourite, checks if movie is favourite`() = runTest {
        val testMovieId = 123
        sut.isMovieFavourite(testMovieId)

        coVerifyOnce { storage.isFavourite(testMovieId) }
        confirmVerified(storage)
    }

    @org.junit.jupiter.api.Test
    fun `Check that getAllFavouriteMovieId, get all favourite movies Ids`() = runTest {
        sut.getAllFavouriteMovieId()

        coVerifyOnce { storage.getFavourites() }
        confirmVerified(storage)
    }
}
