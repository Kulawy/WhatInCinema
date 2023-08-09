package com.gerapp.whatincinema.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest.Builder
import com.gerapp.whatincinema.data.ImagePathProvider
import com.gerapp.whatincinema.domain.data.MovieSnap
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesSearchBar(
    modifier: Modifier = Modifier,
    suggestions: LazyPagingItems<MovieSnap>? = null,
    updateSuggestions: (String?) -> Unit = {},
    onSearch: (String) -> Unit = {},
) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box(modifier.fillMaxWidth()) {
        Box(Modifier.semantics { isContainer = true }.zIndex(1f).fillMaxWidth()) {
            SearchBar(
                modifier = Modifier.align(Alignment.TopCenter),
                query = text,
                onQueryChange = {
                    text = it
                    updateSuggestions(it)
                },
                onSearch = {
//                    onSearch(it)
                    active = false
                },
                active = active,
                onActiveChange = {
                    active = it
                },
                placeholder = { Text("Search here...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = text.isNotBlank(),
                        enter = expandIn(
                            animationSpec = tween(200, easing = FastOutLinearInEasing),
                            expandFrom = Alignment.Center,
                        ) + fadeIn(
                            animationSpec = tween(durationMillis = 200),
                        ),
                        exit = shrinkOut(
                            animationSpec = tween(100, easing = LinearOutSlowInEasing),
                            shrinkTowards = Alignment.Center,
                        ) + fadeOut(
                            animationSpec = tween(durationMillis = 200),
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                updateSuggestions(null)
                                text = ""
                                active = false
                            },
                        )
                    }
                },
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    if (suggestions != null) {
                        items(suggestions.itemCount) { index ->
                            Timber.d("MOVIE SUGGESTION: ${suggestions[index]}")
                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = suggestions[index]?.title ?: "-",
                                        style = TextStyle(color = Color.White),
                                    )
                                },
                                leadingContent = {
                                    AsyncImage(
                                        modifier = modifier.height(height = 64.dp),
                                        model = Builder(LocalContext.current)
                                            .data(
                                                ImagePathProvider.provideOriginalQualityPosterPath(
                                                    suggestions[index]?.posterPath ?: "",
                                                ),
                                            )
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "",
                                        contentScale = ContentScale.Fit,
                                    )
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        text = suggestions[index]?.title ?: ""
                                        active = false
                                    },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MoviesSearchBarPreview() {
    MoviesSearchBar()
}
