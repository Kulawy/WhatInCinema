package com.gerapp.whatincinema.extensions

import kotlinx.coroutines.flow.FlowCollector

suspend inline fun <T> FlowCollector<Result<T>>.emitSuccess(value: T) {
    emit(Result.success(value))
}

suspend inline fun FlowCollector<Result<Nothing>>.emitError(error: Throwable) {
    emit(Result.failure(error))
}
