package com.gerapp.whatincinema.ui.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gerapp.whatincinema.data.ImagePathProvider
import com.gerapp.whatincinema.ui.theme.WhatInCinemaTheme
import com.gerapp.whatincinema.ui.utils.LocalDim

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    title: String = "",
    releaseDate: String = "",
    imagePath: String = "",
    isFavourite: Boolean = false,
    onMovieClick: () -> Unit = {},
    onFavouriteClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onMovieClick() }
            .clip(shape = RoundedCornerShape(15.dp)),
    ) {
        AsyncImage(
            modifier = modifier.fillMaxSize().blur(4.dp).alpha(0.75F),
            model = ImageRequest.Builder(LocalContext.current)
                .data(ImagePathProvider.provideOriginalQualityPosterPath(imagePath))
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(LocalDim.current.spaceExtraSmall),
        ) {
            FavouriteIcon(
                modifier = Modifier.align(Alignment.TopEnd),
                onFavouriteClick = onFavouriteClick,
                isFavourite = isFavourite,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LocalDim.current.spaceExtraSmall)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 20.sp,
                    )
                    Text(
                        text = releaseDate,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
                Spacer(modifier = Modifier.size(LocalDim.current.spaceExtraSmall))
            }
        }
    }
}

@Composable
fun FavouriteIcon(modifier: Modifier, onFavouriteClick: () -> Unit, isFavourite: Boolean) {
    val iconColor = if (isFavourite) Color.Yellow else Color.Gray
    IconButton(
        onClick = onFavouriteClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Outlined.Star,
            contentDescription = "Favourites",
            modifier = modifier.size(LocalDim.current.favouritesListButtonSize),
            tint = iconColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    WhatInCinemaTheme {
        MovieCard()
    }
}
