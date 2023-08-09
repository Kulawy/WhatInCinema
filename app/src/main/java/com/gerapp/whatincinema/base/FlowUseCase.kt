package com.gerapp.whatincinema.base

import com.gerapp.whatincinema.extensions.emitError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(parameters: P): Flow<Result<R>> = execute(parameters)
        .catch { e ->
            Timber.e(e.message ?: "no error message", e)
            emitError(e)
        }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<Result<R>>
}
