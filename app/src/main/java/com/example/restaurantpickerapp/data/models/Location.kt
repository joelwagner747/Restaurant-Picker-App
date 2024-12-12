package com.example.restaurantpickerapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: Double? = null,
    val lng: Double? = null
)

@Serializable
data class Geometry(
    val location: Location? = null
)

@Serializable
data class Result(
    val geometry: Geometry? = null
)

@Serializable
data class LocationResponse(
    val result: Result? = null
)


