package com.example.restaurantpickerapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    @SerialName(value = "place_id")
    val id: String? = null,
    val name: String? = null,
    @SerialName(value = "price_level")
    val priceLevel: Int = -1,
    val rating: Double = -1.0,
    @SerialName(value = "user_ratings_total")
    val numOfUserRatings: Int = -1,
    @SerialName(value = "vicinity")
    val address: String? = null,
    @SerialName(value = "opening_hours")
    val openHours: OpenHours? = null,
    @SerialName(value = "business_status")
    val businessStatus: String? = null,
    var keyword: String? = null,
    val photos: List<Photos>? = null
)

@Serializable
data class RestaurantResponse(
    val results: List<Restaurant>,
    @SerialName(value = "next_page_token")
    val nextPageToken: String? = null
)

@Serializable
data class OpenHours(
    @SerialName(value = "open_now")
    val openNow: Boolean
)

@Serializable
data class Photos(
    val width: Int? = null,
    @SerialName(value = "photo_reference")
    val photoReference: String? = null
)