package com.gerapp.whatincinema.data.network.mapper

import com.gerapp.whatincinema.data.network.model.NetworkResponse
import com.gerapp.whatincinema.domain.mapper.DtoResponseIterableMapper
import com.gerapp.whatincinema.domain.mapper.DtoResponseMapper

interface NetworkResponseMapper {
    fun <T, R> mapToResult(
        networkResponse: NetworkResponse<T>,
        responseMapper: DtoResponseMapper<T, R>,
    ): Result<R>

    fun <T, R> mapToResult(
        networkResponse: NetworkResponse<Iterable<T>>,
        mapperResponse: DtoResponseIterableMapper<T, R>,
    ): Result<Iterable<R>>
}
