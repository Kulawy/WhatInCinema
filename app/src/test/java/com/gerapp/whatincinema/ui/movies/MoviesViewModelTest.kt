package com.gerapp.whatincinema.ui.movies

import androidx.lifecycle.SavedStateHandle
import com.gerapp.whatincinema.base.UiEffect
import com.gerapp.whatincinema.domain.favourite.FavouriteAddUseCase
import com.gerapp.whatincinema.domain.favourite.FavouriteDeleteUseCase
import com.gerapp.whatincinema.domain.favourite.FetchFavouritesUseCase
import com.gerapp.whatincinema.domain.movie.FetchMoviesUseCase
import com.gerapp.whatincinema.domain.search.SearchMovieUseCase
import com.gerapp.whatincinema.ui.movies.MoviesUiEffect.OpenMovieDetailsScreen
import com.gerapp.whatincinema.ui.movies.MoviesUiIntent.MovieItemClicked
import com.gerapp.whatincinema.util.DispatcherSetupExtension
import com.gerapp.whatincinema.util.MockkInjectorExtension
import com.gerapp.whatincinema.util.TestDispatcher
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(DispatcherSetupExtension::class, MockkInjectorExtension::class)
internal class MoviesViewModelTest {

    @InjectMockKs
    lateinit var sut: MoviesViewModel

    @RelaxedMockK
    lateinit var fetchMoviesUseCase: FetchMoviesUseCase

    @RelaxedMockK
    lateinit var fetchFavouritesUseCase: FetchFavouritesUseCase

    @RelaxedMockK
    lateinit var favouriteAddUseCase: FavouriteAddUseCase

    @RelaxedMockK
    lateinit var favouriteDeleteUseCase: FavouriteDeleteUseCase

    @RelaxedMockK
    lateinit var searchMovieUseCase: SearchMovieUseCase

    @RelaxedMockK
    lateinit var savedStateHandle: SavedStateHandle

    @TestDispatcher
    val defaultDispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @TestDispatcher
    val ioDispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Check that MovieItemClicked triggers OpenMovieDetailsScreen`() = runTest {
        val testMovieId = 123
        val effects = mutableListOf<UiEffect>()
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.uiEffect.collect { effects.add(it) }
        }

        sut.sendIntent(MovieItemClicked(movieId = testMovieId))

        assertTrue(effects.contains(OpenMovieDetailsScreen(movieId = testMovieId)))

        job.cancel()
    }
}
