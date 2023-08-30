package com.gerapp.whatincinema.ui.moviedetails

import com.gerapp.whatincinema.base.UiIntent

sealed class MovieDetailsUiIntent : UiIntent {
    data class LoadDetails(val movieId: Int) : MovieDetailsUiIntent()
    object FavouriteChange : MovieDetailsUiIntent()
    object CloseScreen : MovieDetailsUiIntent()
}
