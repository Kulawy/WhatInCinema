package com.gerapp.whatincinema.data.network.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NowPlayingResponseDto(
    @Contextual
    @SerialName("dates")
    var dates: PlayingDatesDto?,
    @SerialName("page") var page: Int? = null,
    @Contextual
    @SerialName("results")
    var results: ArrayList<MovieSnapDto> = arrayListOf(),
    @SerialName("total_pages") var totalPages: Int? = null,
    @SerialName("total_results") var totalResults: Int? = null,
)
