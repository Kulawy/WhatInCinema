package com.gerapp.whatincinema.domain.search

import com.gerapp.whatincinema.base.FlowUseCase
import com.gerapp.whatincinema.di.IoDispatcher
import com.gerapp.whatincinema.domain.data.MovieSnap
import com.gerapp.whatincinema.domain.repository.SearchRepository
import com.gerapp.whatincinema.extensions.emitError
import com.gerapp.whatincinema.extensions.emitSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val SEARCH_MOVIES_PAGE_NUMBER = 1
// It's domain constraint to only load one page for searching to save resources

class SearchMovieUseCase @Inject constructor(
    private val repository: SearchRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<String, List<MovieSnap>>(coroutineDispatcher) {

    override fun execute(parameters: String): Flow<Result<List<MovieSnap>>> = flow {
        try {
            emitSuccess(repository.searchMovie(parameters, SEARCH_MOVIES_PAGE_NUMBER))
        } catch (execute: Error) {
            emitError(execute)
        }
    }
}
