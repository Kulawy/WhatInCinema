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

class FetchFavouritesUseCase @Inject constructor(
    private val repository: FavouriteRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, List<Int>>(coroutineDispatcher) {

    override fun execute(parameters: Unit): Flow<Result<List<Int>>> = flow {
        try {
            emitSuccess(repository.getFavouriteMoviesIds())
        } catch (execute: Error) {
            emitError(execute)
        }
    }
}
