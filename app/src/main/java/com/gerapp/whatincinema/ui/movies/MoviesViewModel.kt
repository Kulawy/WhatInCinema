package com.gerapp.whatincinema.ui.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gerapp.whatincinema.base.MviViewModel
import com.gerapp.whatincinema.base.errors.ConnectionThrowable
import com.gerapp.whatincinema.di.DefaultDispatcher
import com.gerapp.whatincinema.di.IoDispatcher
import com.gerapp.whatincinema.domain.favourite.FavouriteAddUseCase
import com.gerapp.whatincinema.domain.favourite.FavouriteDeleteUseCase
import com.gerapp.whatincinema.domain.favourite.FetchFavouritesUseCase
import com.gerapp.whatincinema.domain.model.MovieSnap
import com.gerapp.whatincinema.domain.movie.FetchMoviesUseCase
import com.gerapp.whatincinema.domain.search.SearchMovieUseCase
import com.gerapp.whatincinema.ui.movies.MoviesUiEffect.OnConnectionError
import com.gerapp.whatincinema.ui.movies.MoviesUiEffect.OnUnspecifiedError
import com.gerapp.whatincinema.ui.movies.MoviesUiEffect.OpenMovieDetailsScreen
import com.gerapp.whatincinema.ui.movies.MoviesUiIntent.FavouriteIconClicked
import com.gerapp.whatincinema.ui.movies.MoviesUiIntent.MovieItemClicked
import com.gerapp.whatincinema.ui.movies.MoviesUiIntent.MoviesScreenStart
import com.gerapp.whatincinema.ui.movies.MoviesUiIntent.SearchMoviesByQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject internal constructor(
    private val moviesUseCase: FetchMoviesUseCase,
    private val favouritesUseCase: FetchFavouritesUseCase,
    private val favouriteAddUseCase: FavouriteAddUseCase,
    private val favouriteDeleteUseCase: FavouriteDeleteUseCase,
    private val searchMovieUseCase: SearchMovieUseCase,
    savedStateHandle: SavedStateHandle,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MviViewModel<MoviesUiIntent, MoviesScreenUiState, MoviesUiEffect>(
    MoviesScreenUiState(isLoading = true),
    savedStateHandle,
) {
    private val MOVIES_PAGE_SIZE = 20

    init {
        loadAllMovies()
    }

    override fun onUiIntent(intent: MoviesUiIntent) {
        when (intent) {
            is MoviesScreenStart -> viewModelScope.launch(defaultDispatcher) {
                refreshFavourites()
            }

            is MovieItemClicked -> onMovieItemClicked(movieId = intent.movieId)
            is FavouriteIconClicked -> onFavouriteIconClicked(intent.movieId)
            is SearchMoviesByQuery -> searchMovie(intent.query)
        }
    }

    private fun loadAllMovies() {
        viewModelScope.launch(ioDispatcher) {
            moviesUseCase(MOVIES_PAGE_SIZE)
                .onEach { result ->
                    result.onSuccess {
                        updateMovieStream(it)
                        refreshFavourites()
                    }.onFailure {
                        publishEffect(OnUnspecifiedError)
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun updateMovieStream(data: Pager<Int, MovieSnap>) =
        publishState { copy(moviesPagingDataFlow = data.flow.cachedIn(viewModelScope)) }

    private fun onMovieItemClicked(movieId: Int) =
        publishEffect(OpenMovieDetailsScreen(movieId = movieId))

    private fun searchMovie(query: String?) {
        if (!query.isNullOrEmpty()) {
            searchMovieUseCase(query).onEach { result ->
                result.fold(
                    {
                        publishState {
                            copy(
                                searchPagingDataFlow = flowOf(PagingData.from(it)).cachedIn(
                                    viewModelScope,
                                ),
                            )
                        }
                    },
                    {
                        when (it) {
                            is ConnectionThrowable -> publishEffect(OnConnectionError)
                            else -> publishEffect(OnUnspecifiedError)
                        }
                    },
                )
            }.flowOn(defaultDispatcher).launchIn(viewModelScope)
        } else {
            publishState { copy(searchPagingDataFlow = null) }
        }
    }

    private fun onFavouriteIconClicked(movieId: Int) {
        val isFavourite = uiState.value.favouriteIdsList.contains(movieId)
        if (isFavourite) {
            favouriteDeleteUseCase(movieId).onCompletion {
                refreshFavourites()
            }.flowOn(defaultDispatcher).launchIn(viewModelScope)
        } else {
            favouriteAddUseCase(movieId).onCompletion {
                refreshFavourites()
            }.flowOn(defaultDispatcher).launchIn(viewModelScope)
        }
    }

    private suspend fun refreshFavourites() {
        favouritesUseCase(Unit).collect { result ->
            result.fold(
                { publishState { copy(favouriteIdsList = it) } },
                {
                    publishEffect(OnUnspecifiedError)
                },
            )
        }
    }

    private fun clearSearch() {
        viewModelScope.launch(defaultDispatcher) {
            publishState { copy(searchPagingDataFlow = null) }
        }
    }
}
