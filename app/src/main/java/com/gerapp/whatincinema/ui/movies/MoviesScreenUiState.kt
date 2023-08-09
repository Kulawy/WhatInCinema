package com.gerapp.whatincinema.ui.movies

import androidx.paging.PagingData
import com.gerapp.whatincinema.base.UiState
import com.gerapp.whatincinema.domain.data.MovieSnap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MoviesScreenUiState(
    val favouriteIdsList: List<Int> = listOf(),
    val searchPagingDataFlow: Flow<PagingData<MovieSnap>>? = null,
//    val searchResultPager: Pager<Int, MovieSnap>? = null,
    val moviesPagingDataFlow: Flow<PagingData<MovieSnap>> = flowOf(),
//    val moviesPager: Pager<Int, MovieSnap>? = null,
    val error: Boolean = false,
    val isLoading: Boolean,
) : UiState {

    fun getMoviesToDisplay() =
        searchPagingDataFlow ?: moviesPagingDataFlow
}
