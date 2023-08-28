package com.gerapp.whatincinema.domain.movie

import com.gerapp.whatincinema.base.FlowUseCase
import com.gerapp.whatincinema.di.IoDispatcher
import com.gerapp.whatincinema.domain.model.MovieDetails
import com.gerapp.whatincinema.domain.repository.MovieRepository
import com.gerapp.whatincinema.extensions.emitError
import com.gerapp.whatincinema.extensions.emitSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<Int, MovieDetails>(coroutineDispatcher) {

    override fun execute(parameters: Int): Flow<Result<MovieDetails>> = flow {
        repository.getMovie(parameters).fold(
            { emitSuccess(it) },
            {
                Timber.d(it, "Problem with fetching movie")
                emitError(it)
            },
        )
    }
}
