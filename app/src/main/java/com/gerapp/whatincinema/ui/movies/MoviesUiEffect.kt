package com.gerapp.whatincinema.ui.movies

import com.gerapp.whatincinema.base.UiEffect

sealed class MoviesUiEffect : UiEffect {
    data class OpenMovieDetailsScreen(val movieId: Int) : MoviesUiEffect()
    object OnConnectionError : MoviesUiEffect()
    object OnServerError : MoviesUiEffect()
    object OnUnspecifiedError : MoviesUiEffect()
}
