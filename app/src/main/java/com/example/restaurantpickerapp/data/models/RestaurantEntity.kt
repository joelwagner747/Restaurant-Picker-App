package com.example.restaurantpickerapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class RestaurantEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val priceLevel: Int,
    val rating: Double,
    val numOfUserRatings: Int,
    val address: String,
)
