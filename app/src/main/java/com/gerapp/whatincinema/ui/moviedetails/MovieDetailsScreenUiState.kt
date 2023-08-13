package com.gerapp.whatincinema.ui.moviedetails

import com.gerapp.whatincinema.base.UiState
import com.gerapp.whatincinema.ui.model.MovieDetailsUiModel

data class MovieDetailsScreenUiState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val movie: MovieDetailsUiModel? = null,
    val isFavourite: Boolean = false,
) : UiState
