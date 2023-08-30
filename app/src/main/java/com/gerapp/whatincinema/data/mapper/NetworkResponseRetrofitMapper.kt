package com.gerapp.whatincinema.data.mapper

import com.gerapp.whatincinema.base.errors.ApiThrowable
import com.gerapp.whatincinema.base.errors.ConnectionThrowable
import com.gerapp.whatincinema.data.network.model.NetworkResponse
import com.gerapp.whatincinema.domain.mapper.DtoResponseIterableMapper
import com.gerapp.whatincinema.domain.mapper.DtoResponseMapper
import javax.inject.Inject

class NetworkResponseRetrofitMapper @Inject constructor() : NetworkResponseMapper {

    override fun <T, R> mapToResult(
        networkResponse: NetworkResponse<T>,
        responseMapper: DtoResponseMapper<T, R>,
    ): Result<R> {
        return when (networkResponse) {
            is NetworkResponse.Success -> Result.success(responseMapper.toDomain(networkResponse.data))
            is NetworkResponse.Error.ApiError -> Result.failure(
                ApiThrowable(
                    networkResponse.apiErrorMsg,
                    networkResponse.apiErrorCode,
                ),
            )

            is NetworkResponse.Error.NetworkError -> Result.failure(
                ConnectionThrowable(
                    networkResponse.networkMsg,
                ),
            )

            is NetworkResponse.Error.InternalError -> Result.failure(networkResponse.cause)
        }
    }

    override fun <T, R> mapToResult(
        networkResponse: NetworkResponse<Iterable<T>>,
        mapperResponse: DtoResponseIterableMapper<T, R>,
    ): Result<Iterable<R>> {
        return when (networkResponse) {
            is NetworkResponse.Success -> Result.success(
                mapperResponse.mapIterableToDomain(
                    networkResponse.data,
                ),
            )

            is NetworkResponse.Error.ApiError -> Result.failure(
                ApiThrowable(
                    networkResponse.apiErrorMsg,
                    networkResponse.apiErrorCode,
                ),
            )

            is NetworkResponse.Error.NetworkError -> Result.failure(
                ConnectionThrowable(
                    networkResponse.networkMsg,
                ),
            )

            is NetworkResponse.Error.InternalError -> Result.failure(networkResponse.cause)
        }
    }
}
