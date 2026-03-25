package com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StepikSearchResponse(
    @SerialName("meta")
    val meta: MetaResponse,
    @SerialName("search-results")
    val searchResults: List<SearchResultItem>
)
