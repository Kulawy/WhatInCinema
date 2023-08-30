package com.gerapp.whatincinema.domain.data

import com.gerapp.whatincinema.data.network.model.DtoModel

interface DomainModel {
    fun toDto(): DtoModel
}
