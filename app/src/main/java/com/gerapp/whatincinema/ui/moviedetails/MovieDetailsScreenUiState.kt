package com.gerapp.whatincinema.ui.moviedetails

import com.gerapp.whatincinema.base.UiState

data class MovieDetailsScreenUiState(
    val isLoading: Boolean = false,
    val movie: MovieDetailsUiModel? = null,
    val isFavourite: Boolean = false,
) : UiState
