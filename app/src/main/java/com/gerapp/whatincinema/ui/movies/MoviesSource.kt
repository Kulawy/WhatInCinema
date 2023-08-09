package com.gerapp.whatincinema.ui.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gerapp.whatincinema.domain.data.MovieSnap
import com.gerapp.whatincinema.domain.repository.MovieRepository
import timber.log.Timber
import java.io.IOException

class MoviesSource(
    private val repository: MovieRepository,
) : PagingSource<Int, MovieSnap>() {

    override fun getRefreshKey(state: PagingState<Int, MovieSnap>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSnap> {
        Timber.d("FETCHING")
        return try {
            val nextPage = params.key ?: 1 // Starts from page 1
            val moviesList = repository.getMovies(page = nextPage)
            LoadResult.Page(
                data = moviesList,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (moviesList.isEmpty()) null else nextPage + 1,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }
}
