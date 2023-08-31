package com.gerapp.whatincinema.ui.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gerapp.whatincinema.base.MviViewModel
import com.gerapp.whatincinema.base.errors.ConnectionThrowable
import com.gerapp.whatincinema.di.DefaultDispatcher
import com.gerapp.whatincinema.domain.favourite.FavouriteAddUseCase
import com.gerapp.whatincinema.domain.favourite.FavouriteCheckUseCase
import com.gerapp.whatincinema.domain.favourite.FavouriteDeleteUseCase
import com.gerapp.whatincinema.domain.movie.GetMovieDetailsUseCase
import com.gerapp.whatincinema.ui.moviedetails.MovieDetailsUiEffect.OnCloseScreen
import com.gerapp.whatincinema.ui.moviedetails.MovieDetailsUiEffect.OnFetchDetailsConnectionError
import com.gerapp.whatincinema.ui.moviedetails.MovieDetailsUiEffect.OnUnspecifiedError
import com.gerapp.whatincinema.ui.moviedetails.MovieDetailsUiIntent.CloseScreen
import com.gerapp.whatincinema.ui.moviedetails.MovieDetailsUiIntent.FavouriteChange
import com.gerapp.whatincinema.ui.moviedetails.MovieDetailsUiIntent.LoadDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
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
) : MviViewModel<MovieDetailsUiIntent, MovieDetailsScreenUiState, MovieDetailsUiEffect>(
    MovieDetailsScreenUiState(
        isLoading = true,
    ),
    savedStateHandle,
) {

    override fun onUiIntent(intent: MovieDetailsUiIntent) {
        when (intent) {
            is FavouriteChange -> onFavouriteChange()
            is LoadDetails -> fetchMovieDetails(intent.movieId)
            is CloseScreen -> publishEffect(OnCloseScreen)
        }
    }

    private fun onFavouriteChange() {
        Timber.d("ON FAVOURITE CHANGE START: ${uiState.value.isFavourite}")
        if (uiState.value.isFavourite) {
            uiState.value.movie?.let { movieDetails ->
                favouriteDeleteUseCase(movieDetails.movieId).flowOn(defaultDispatcher)
                    .launchIn(viewModelScope)
            }
        } else {
            uiState.value.movie?.let { movieDetails ->
                favouriteAddUseCase(movieDetails.movieId).flowOn(defaultDispatcher)
                    .launchIn(viewModelScope)
            }
        }
        publishState { copy(isFavourite = !isFavourite) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchMovieDetails(movieId: Int) {
        getMovieDetailsUseCase(movieId).flatMapMerge { result ->
            flowOf(
                result.fold(
                    {
                        Result.success(
                            MovieDetailsUiModel(it),
                        )
                    },
                    {
                        Result.failure(it)
                    },
                ),
            )
        }.onStart {
            publishState { copy(isLoading = true) }
        }.onEach { movieDetailsResult ->
            movieDetailsResult.onSuccess {
                publishState { copy(movie = it) }
            }
            movieDetailsResult.onFailure {
                Timber.d("Details Failure: $it")
                fetchMovieDetailsFailureHandler(it)
            }
        }.onCompletion {
            publishState { copy(isLoading = false) }
        }.launchIn(viewModelScope)

        favouriteCheckUseCase(movieId).onEach { isFavouriteResult ->
            isFavouriteResult.onSuccess {
                publishState { copy(isFavourite = it) }
            }
        }.launchIn(viewModelScope)
    }

    private fun fetchMovieDetailsFailureHandler(error: Throwable) =
        when (error) {
            is ConnectionThrowable -> publishEffect(OnFetchDetailsConnectionError)
            else -> publishEffect(OnUnspecifiedError)
        }
}
