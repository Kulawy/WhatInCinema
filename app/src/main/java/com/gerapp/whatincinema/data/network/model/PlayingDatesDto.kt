package com.gerapp.whatincinema.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayingDatesDto(
    @SerialName("minimum") var minimum: String,
    @SerialName("maximum") var maximum: String,
)
