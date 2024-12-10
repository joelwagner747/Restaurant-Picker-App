package com.example.restaurantpickerapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    @SerialName(value = "description")
    val name: String,
    @SerialName(value = "place_id")
    val placeId: String
)
