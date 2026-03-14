package com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("has_next")
    val hasNext: Boolean,
    @SerialName("has_previous")
    val hasPrevious: Boolean
)