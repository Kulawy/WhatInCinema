package com.gerapp.whatincinema.ui.movies

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gerapp.whatincinema.R
import com.gerapp.whatincinema.domain.model.MovieSnap
import com.gerapp.whatincinema.ui.component.ErrorAnnouncement
import com.gerapp.whatincinema.ui.component.ErrorDialog
import com.gerapp.whatincinema.ui.component.LoadingCircular
import com.gerapp.whatincinema.ui.component.MoviesSearchBar
import com.gerapp.whatincinema.ui.movies.MoviesUiEffect.OnConnectionError
import com.gerapp.whatincinema.ui.movies.MoviesUiEffect.OnServerError
import com.gerapp.whatincinema.ui.movies.MoviesUiEffect.OnUnspecifiedError
import com.gerapp.whatincinema.ui.movies.MoviesUiEffect.OpenMovieDetailsScreen
import com.gerapp.whatincinema.ui.movies.MoviesUiIntent.FavouriteIconClicked
import com.gerapp.whatincinema.ui.movies.MoviesUiIntent.MovieItemClicked
import com.gerapp.whatincinema.ui.movies.MoviesUiIntent.MoviesScreenStart
import com.gerapp.whatincinema.ui.movies.MoviesUiIntent.SearchMoviesByQuery
import com.gerapp.whatincinema.ui.utils.LocalDim
import com.gerapp.whatincinema.ui.utils.LocalDrawOrder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

const val COLUMNS_NUMBER = 2

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "FlowOperatorInvokedInComposition",
    "CoroutineCreationDuringComposition",
)
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    navigateToMovieDetails: (Int) -> Unit = {},
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    val moviesScreenUiState by viewModel.uiState.collectAsState()
    val networkErrorDialogShow = remember { mutableStateOf(false) }
    viewModel.sendIntent(MoviesScreenStart)

    val onSearchRun: (String?) -> Unit = { query ->
        viewModel.sendIntent(SearchMoviesByQuery(query))
    }

    LaunchedEffect("MovieScreenEffects") {
        viewModel.uiEffect.onEach {
            when (it) {
                is OpenMovieDetailsScreen -> navigateToMovieDetails(it.movieId)
                is OnConnectionError -> networkErrorDialogShow.value = true
                is OnServerError -> {}
                is OnUnspecifiedError -> {}
            }
        }.launchIn(this)
    }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MoviesSearchBar(
                modifier = modifier.zIndex(LocalDrawOrder.current.onVeryFront),
                suggestions = moviesScreenUiState.searchPagingDataFlow?.collectAsLazyPagingItems(),
                updateSuggestions = onSearchRun,
            )
        },
    ) { paddingValues ->
        Column(
            Modifier.fillMaxSize().padding(paddingValues),
        ) {
            MoviesList(
                movies = moviesScreenUiState.getMoviesToDisplay().collectAsLazyPagingItems(),
                favouriteIdsList = moviesScreenUiState.favouriteIdsList,
                onMovieClick = { movieId -> viewModel.sendIntent(MovieItemClicked(movieId)) },
                onFavouriteClick = { movieId -> viewModel.sendIntent(FavouriteIconClicked(movieId)) },
            )
            if (networkErrorDialogShow.value) {
                ErrorDialog(
                    onDismissRequest = { networkErrorDialogShow.value = false },
                    onConfirm = { networkErrorDialogShow.value = false },
                    isDisplayed = networkErrorDialogShow.value,
                    title = stringResource(id = R.string.dialog_connection_error_title),
                    msg = stringResource(id = R.string.dialog_connection_error_msg),
                )
            }
        }
    }
}

@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<MovieSnap>,
    favouriteIdsList: List<Int>,
    onMovieClick: (Int) -> Unit,
    onFavouriteClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier.padding(LocalDim.current.spaceSmall),
        columns = GridCells.Fixed(COLUMNS_NUMBER),
        verticalArrangement = Arrangement.spacedBy(LocalDim.current.spaceExtraSmall),
        horizontalArrangement = Arrangement.spacedBy(LocalDim.current.spaceExtraSmall),
    ) {
        items(movies.itemCount) { index ->
            movies[index]?.let { movie ->
                MovieCard(
                    title = movie.title,
                    releaseDate = movie.releaseDate ?: stringResource(id = R.string.no_data),
                    isFavourite = favouriteIdsList.contains(movie.id),
                    imagePath = movie.posterPath,
                    onMovieClick = {
                        movie.id.let {
                            Timber.d("DEBUG MOVIE CLICKED ID: $it")
                            onMovieClick(it)
                        }
                    },
                    onFavouriteClick = {
                        onFavouriteClick(movie.id)
                    },
                )
            }
        }
        movies.apply {
            item(
                span = { GridItemSpan(maxLineSpan) },
            ) {
                when {
                    loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                        LoadingCircular(
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                        ErrorAnnouncement(
                            modifier = Modifier.fillMaxSize(),
                            title = stringResource(id = R.string.dialog_error_title),
                            msg = stringResource(id = R.string.dialog_error_message),
                            onConfirm = {
                                retry()
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieScreenPreview() {
    MoviesScreen()
}
