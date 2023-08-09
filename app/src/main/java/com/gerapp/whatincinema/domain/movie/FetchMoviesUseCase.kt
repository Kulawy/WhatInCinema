package com.gerapp.whatincinema.domain.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.gerapp.whatincinema.base.FlowUseCase
import com.gerapp.whatincinema.di.IoDispatcher
import com.gerapp.whatincinema.domain.data.MovieSnap
import com.gerapp.whatincinema.domain.repository.MovieRepository
import com.gerapp.whatincinema.extensions.emitSuccess
import com.gerapp.whatincinema.ui.movies.MoviesSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<Int, Pager<Int, MovieSnap>>(coroutineDispatcher) {

    override fun execute(parameters: Int): Flow<Result<Pager<Int, MovieSnap>>> =
        flow {
            emitSuccess(
                Pager(PagingConfig(parameters)) {
                    MoviesSource(repository)
                },
            )
        }
}

// class FetchMoviesUseCase @Inject constructor(
//    private val repository: MovieRepository,
// ) : PagingUseCase<Int, MovieSnap> {
//
//    override fun invoke(parameter: Int): Pager<Int, MovieSnap> =
//        Pager(PagingConfig(parameter)) { MoviesSource(repository) }
// }
