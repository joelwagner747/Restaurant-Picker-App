package com.example.restaurantpickerapp.data

import com.example.restaurantpickerapp.data.models.City
import com.example.restaurantpickerapp.data.models.Location
import com.example.restaurantpickerapp.data.models.Restaurant

interface PlacesRepository {
    suspend fun getRestaurants(lat: String, lon: String, keywords: List<String>) : List<Restaurant>

    suspend fun getCityByName(cityName: String) : List<City>

    suspend fun getLatLonOfCity(id: String) : Location
}