package com.gerapp.whatincinema.ui.moviedetails

import com.gerapp.whatincinema.base.UiEffect

sealed class MovieDetailsUiEffect : UiEffect {
    object OnFetchDetailsConnectionError : MovieDetailsUiEffect()
    object OnServerError : MovieDetailsUiEffect()
    object OnUnspecifiedError : MovieDetailsUiEffect()
    object OnCloseScreen : MovieDetailsUiEffect()
}
