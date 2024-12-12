package com.example.restaurantpickerapp.data.models

enum class Price(val value: Int, val priceRange: String) {
    ONE(1, "$5 - $15"),
    TWO(2, "$15 - $30"),
    THREE(3, "$30 - $50"),
    FOUR(4, priceRange = "$50+"),
}