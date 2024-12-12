package com.example.restaurantpickerapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    @SerialName(value = "description")
    val name: String? = null,
    @SerialName(value = "place_id")
    val placeId: String? = null
)

@Serializable
data class CitySuggestionsResponse(
    val predictions: List<City>? = null
)