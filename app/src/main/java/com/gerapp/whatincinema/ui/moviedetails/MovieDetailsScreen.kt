package com.gerapp.whatincinema.ui.moviedetails

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.ListenableWorker
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gerapp.whatincinema.R
import com.gerapp.whatincinema.data.ImagePathProvider
import com.gerapp.whatincinema.ui.component.ErrorDialog
import com.gerapp.whatincinema.ui.component.LoadingCircular
import com.gerapp.whatincinema.ui.model.MovieDetailsUiModel
import com.gerapp.whatincinema.ui.moviedetails.MovieDetailsUiIntent.FavouriteChange
import com.gerapp.whatincinema.ui.theme.Typography
import com.gerapp.whatincinema.ui.utils.LocalDim
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    movieId: Int = -1,
) {
    Timber.d("MOVIE DETAILS SCREEN RUN")
    viewModel.sendIntent(MovieDetailsUiIntent.LoadDetails(movieId))
    val flowState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
            .padding()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            Column(modifier = Modifier) {
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                        )
                        .fillMaxWidth()
                        .statusBarsPadding(),
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier,
                            tint = Color.Black,
                        )
                    }
                    FavouriteIcon(
                        modifier = Modifier,
                        onFavouriteClick = { viewModel.sendIntent(FavouriteChange) },
                        isFavourite = flowState.isFavourite,
                    )
                }
            }
        },
    ) { paddingValues ->
        flowState.movie?.let { movieDetails ->
            Column(modifier = modifier.verticalScroll(rememberScrollState())) {
                MovieDetailsImage(
                    modifier = modifier,
                    imagePath = movieDetails.posterPath,
                )
                Spacer(modifier = Modifier.size(LocalDim.current.spaceSmall))
                MovieDetailsContent(
                    modifier = modifier.padding(LocalDim.current.spaceSmall),
                    movieDetails = movieDetails,
                )
            }
        }

        flowState.error?.let { error ->
            ErrorDialog(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.dialog_error_title),
                msg = stringResource(id = R.string.dialog_error_message),
                onConfirm = {
                    ListenableWorker.Result.retry()
                },
                onDismissRequest = {},
            )
        }

        if (flowState.isLoading) {
            LoadingCircular(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
fun MovieDetailsContent(modifier: Modifier, movieDetails: MovieDetailsUiModel) {
    Column(
        modifier = modifier.padding(
            start = LocalDim.current.spaceMedium,
            end = LocalDim.current.spaceMedium,
            bottom = LocalDim.current.spaceExtraLarge,
        ),
    ) {
        MovieDetailsTitleComponent(movieDetails.title)
        MovieDetailsInfoComponent(
            stringResource(id = R.string.date_label),
            movieDetails.releaseDate,
        )
        MovieDetailsInfoComponent(
            stringResource(id = R.string.rate_label),
            movieDetails.voteAverage.toString(),
        )
        MovieDetailsInfoComponent(
            stringResource(id = R.string.description_label),
            movieDetails.overview,
        )
    }
}

@Composable
fun MovieDetailsTitleComponent(movieTitle: String) {
    Text(text = stringResource(id = R.string.title_label))
    Text(
        text = movieTitle,
        style = Typography.titleLarge,
    )
}

@Composable
fun MovieDetailsInfoComponent(label: String, content: String) {
    Text(
        text = label,
        fontStyle = FontStyle.Italic,
    )
    Text(
        text = content,
        style = Typography.titleMedium,
    )
}

@Composable
fun MovieDetailsImage(
    modifier: Modifier,
    imagePath: String,
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(ImagePathProvider.provideOriginalQualityPosterPath(imagePath))
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.poster_content_description),
            contentScale = ContentScale.FillWidth,
            error = painterResource(id = R.drawable.ic_broken_image_100),
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 25F, bottomEnd = 25F))
                .fillMaxWidth(),
        )
    }
}

@Composable
fun FavouriteIcon(modifier: Modifier, onFavouriteClick: () -> Unit, isFavourite: Boolean) {
    val iconColor = if (isFavourite) Color.Yellow else Color.Gray
    IconButton(
        onClick = onFavouriteClick,
        modifier = modifier
            .wrapContentSize(),
    ) {
        Icon(
            imageVector = Icons.Outlined.Star,
            contentDescription = stringResource(id = R.string.icn_favourite_content_description),
            modifier = modifier
                .size(LocalDim.current.favouritesIconSize),
            tint = iconColor,
        )
    }
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    MovieDetailsScreen(onBackClick = {})
}
