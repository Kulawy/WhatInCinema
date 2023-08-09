package com.gerapp.whatincinema.ui.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gerapp.whatincinema.base.MviViewModel
import com.gerapp.whatincinema.di.DefaultDispatcher
import com.gerapp.whatincinema.domain.favourite.FavouriteAddUseCase
import com.gerapp.whatincinema.domain.favourite.FavouriteCheckUseCase
import com.gerapp.whatincinema.domain.favourite.FavouriteDeleteUseCase
import com.gerapp.whatincinema.domain.movie.GetMovieDetailsUseCase
import com.gerapp.whatincinema.ui.moviedetails.MovieDetailsUiIntent.FavouriteChange
import com.gerapp.whatincinema.ui.moviedetails.MovieDetailsUiIntent.LoadDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val favouriteCheckUseCase: FavouriteCheckUseCase,
    private val favouriteAddUseCase: FavouriteAddUseCase,
    private val favouriteDeleteUseCase: FavouriteDeleteUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
) : MviViewModel<MovieDetailsUiIntent, MovieDetailsScreenUiState>(
    MovieDetailsScreenUiState(
        isLoading = true,
    ),
    savedStateHandle,
) {

    override fun onUiIntent(intent: MovieDetailsUiIntent) {
        when (intent) {
            is FavouriteChange -> onFavouriteChange()
            is LoadDetails -> fetchMovieDetails(intent.movieId)
//            is OnCategoriesScreenStart -> refreshLocalCart()
            else -> {
            }
        }
    }

    private fun onFavouriteChange() {
        Timber.d("ON FAVOURITE CHANGE START: ${uiState.value.isFavourite}")
        if (uiState.value.isFavourite) {
            uiState.value.movie?.let { movieDetails ->
                favouriteDeleteUseCase(movieDetails.id)
                    .flowOn(defaultDispatcher)
                    .launchIn(viewModelScope)
            }
        } else {
            uiState.value.movie?.let { movieDetails ->
                favouriteAddUseCase(movieDetails.id)
                    .flowOn(defaultDispatcher)
                    .launchIn(viewModelScope)
            }
        }
        publishState { copy(isFavourite = !isFavourite) }
    }

    private fun fetchMovieDetails(movieId: Int) {
        getMovieDetailsUseCase(movieId)
            .onStart {
                publishState { copy(isLoading = true) }
            }.onEach { movieDetailsResult ->
                movieDetailsResult.onSuccess {
                    publishState { copy(movie = it) }
                }
            }.onCompletion {
                publishState { copy(isLoading = false) }
            }.launchIn(viewModelScope)

        favouriteCheckUseCase(movieId)
            .onEach { isFavouriteResult ->
                isFavouriteResult.onSuccess {
                    publishState { copy(isFavourite = it) }
                }
            }.launchIn(viewModelScope)
    }
}
