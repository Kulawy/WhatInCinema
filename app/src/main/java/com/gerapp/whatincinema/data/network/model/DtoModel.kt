package com.gerapp.whatincinema.data.network.model

import com.gerapp.whatincinema.domain.data.DomainModel

interface DtoModel {
    fun toDomain(): DomainModel
}
