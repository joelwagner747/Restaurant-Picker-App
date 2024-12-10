package com.example.restaurantpickerapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: String,
    val lng: String
)