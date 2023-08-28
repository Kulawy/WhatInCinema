package com.gerapp.whatincinema.data.network.mapper

import com.gerapp.whatincinema.data.network.model.NetworkResponse
import retrofit2.HttpException
import java.io.IOException
import java.lang.IllegalStateException
import javax.inject.Inject

class ResponseApiWrapper @Inject constructor() {

    fun <T> wrapResponse(body: T): NetworkResponse<T> {
        return NetworkResponse.Success<T>(body)
    }

    fun <T> wrapError(throwable: Throwable): NetworkResponse<T> {
        return when (throwable) {
            is HttpException -> {
                apiError(throwable.code(), throwable.message())
            }

            is IOException -> networkError(throwable.message ?: "Connection error")
            is IllegalStateException -> internalError(throwable.cause!!)
            else -> internalError(throwable)
        }
    }

    private fun apiError(apiErrorCode: Int, apiErrorMsg: String): NetworkResponse.Error.ApiError =
        NetworkResponse.Error.ApiError(apiErrorCode, apiErrorMsg)

    private fun networkError(apiErrorMsg: String): NetworkResponse.Error.NetworkError =
        NetworkResponse.Error.NetworkError(apiErrorMsg)

    private fun internalError(cause: Throwable): NetworkResponse.Error.InternalError =
        NetworkResponse.Error.InternalError(cause)
}
