package com.gerapp.whatincinema.domain.mapper

interface DtoResponseIterableMapper<Dto, DomainModel> {
    fun mapIterableToDomain(dto: Iterable<Dto>): Iterable<DomainModel>
}
