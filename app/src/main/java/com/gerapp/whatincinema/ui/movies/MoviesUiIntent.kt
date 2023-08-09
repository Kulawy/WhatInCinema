package com.gerapp.whatincinema.ui.movies

import com.gerapp.whatincinema.base.UiIntent

sealed class MoviesUiIntent : UiIntent {
    object MoviesScreenStart : MoviesUiIntent()
    data class MovieItemClicked(val movieId: Int) : MoviesUiIntent()
    data class FavouriteIconClicked(val movieId: Int) : MoviesUiIntent()
    data class SearchMoviesByQuery(val query: String?) : MoviesUiIntent()
}
