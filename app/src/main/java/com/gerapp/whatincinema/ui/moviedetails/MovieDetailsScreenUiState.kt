package com.gerapp.whatincinema.ui.moviedetails

import com.gerapp.whatincinema.base.UiState
import com.gerapp.whatincinema.domain.data.MovieDetails

data class MovieDetailsScreenUiState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val movie: MovieDetails? = null,
    val isFavourite: Boolean = false,
) : UiState
