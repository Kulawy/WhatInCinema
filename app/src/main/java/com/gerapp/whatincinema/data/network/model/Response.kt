package com.gerapp.whatincinema.data.network.model

sealed class Response<out T> {

    data class Success<out T>(
        val data: T?,
    ) : Response<T>()

    data class Failure(
        val e: Exception?,
    ) : Response<Nothing>()
}


