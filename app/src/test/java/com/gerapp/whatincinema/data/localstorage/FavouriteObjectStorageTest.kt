package com.gerapp.whatincinema.data.localstorage

import com.gerapp.whatincinema.util.DispatcherSetupExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/*
    FavouriteObjectStorage is not external tool/library
    That's why I also test this Storage without mock

 */

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(DispatcherSetupExtension::class)
internal class FavouriteObjectStorageTest {

    private val sut = FavouriteObjectStorage

    @Test
    fun `Check that if after addFavourite, movieId isFavourite return true`() = runTest {
        val movieId = 123
        sut.addFavourite(movieId)
        Assertions.assertTrue(sut.isFavourite(movieId))
        sut.deleteFavourite(movieId)
    }

    @Test
    fun `Check that if after deleteFavourite, movieId isFavourite return false`() = runTest {
        val movieId = 123
        sut.addFavourite(movieId)
        sut.deleteFavourite(movieId)
        Assertions.assertFalse(sut.isFavourite(movieId))
    }

    @Test
    fun `Check that if after couple addFavourite for movieId, isFavourite for movieId return true and number of favourites movies is 1`() =
        runTest {
            val testMovieId = 123
            for (i in 0..10) {
                sut.addFavourite(testMovieId)
            }
            Assertions.assertTrue(sut.isFavourite(movieId = testMovieId))
            Assertions.assertEquals(sut.getFavourites().size, 1)
            sut.deleteFavourite(testMovieId)
        }

    @Test
    fun `Check that if after multiple addFavourite, getFavourites return list of all added movieIds`() =
        runTest {
            val movieIds = listOf(123, 321, 111, 222, 333)
            movieIds.forEach {
                sut.addFavourite(it)
            }
            Assertions.assertArrayEquals(sut.getFavourites().toIntArray(), movieIds.toIntArray())
            movieIds.forEach {
                sut.deleteFavourite(it)
            }
        }

    @Test
    fun `Check that if no favourite added, getFavourites return empty List`() =
        runTest {
            Assertions.assertTrue(sut.getFavourites()::isEmpty)
        }
}