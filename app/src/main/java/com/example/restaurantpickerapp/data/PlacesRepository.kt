package com.example.restaurantpickerapp.data

import com.example.restaurantpickerapp.data.models.City
import com.example.restaurantpickerapp.data.models.Location
import com.example.restaurantpickerapp.data.models.Price
import com.example.restaurantpickerapp.data.models.Restaurant

interface PlacesRepository {
    suspend fun getRestaurants(cityId: String, meal: String, keywords: List<String>, price: Price) : List<Restaurant>

    suspend fun getCityByName(cityName: String, state: String) : List<City>
}