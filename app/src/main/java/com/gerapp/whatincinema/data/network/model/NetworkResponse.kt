package com.gerapp.whatincinema.data.network.model

sealed class NetworkResponse<out T> {

    data class Success<out T>(val data: T) : NetworkResponse<T>()

    sealed class Error(val msg: String) : NetworkResponse<Nothing>() {
        data class ApiError(val apiErrorCode: Int, val apiErrorMsg: String) : Error(apiErrorMsg)
        data class InternalError(val cause: Throwable) : Error(cause.message ?: "")
        data class NetworkError(val network_msg: String) : Error(network_msg)
    }
}
