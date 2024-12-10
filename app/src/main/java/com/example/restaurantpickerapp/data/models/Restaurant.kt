package com.example.restaurantpickerapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val name: String,
    @SerialName(value = "price_level")
    val priceLevel: Int = -1,
    val rating: Double,
    @SerialName(value = "user_ratings_total")
    val numOfUserRatings: Int,
    @SerialName(value = "vicinity")
    val address: String,
    @SerialName(value = "open_now")
    val openNow: Boolean,
    val businessStatus: String
)