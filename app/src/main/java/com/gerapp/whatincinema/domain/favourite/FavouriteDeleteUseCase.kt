package com.gerapp.whatincinema.domain.favourite

import com.gerapp.whatincinema.base.FlowUseCase
import com.gerapp.whatincinema.di.IoDispatcher
import com.gerapp.whatincinema.domain.repository.FavouriteRepository
import com.gerapp.whatincinema.extensions.emitError
import com.gerapp.whatincinema.extensions.emitSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavouriteDeleteUseCase @Inject constructor(
    private val repository: FavouriteRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<Int, Unit>(coroutineDispatcher) {

    override fun execute(parameters: Int): Flow<Result<Unit>> = flow {
        try {
            emitSuccess(repository.removeMovieFormFavourites(parameters))
        } catch (execute: Error) {
            emitError(execute)
        }
    }
}
