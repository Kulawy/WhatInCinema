package com.gerapp.whatincinema.domain.mapper

interface DtoResponseMapper<Dto, DomainModel> {
    fun toDomain(dto: Dto): DomainModel
}
