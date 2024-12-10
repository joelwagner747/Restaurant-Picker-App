package com.example.restaurantpickerapp.data

import com.example.restaurantpickerapp.data.models.City
import com.example.restaurantpickerapp.data.models.Location
import com.example.restaurantpickerapp.data.models.Restaurant
import com.example.restaurantpickerapp.network.GooglePlacesApiService

class GooglePlacesRepository(
    private val googlePlacesApiService: GooglePlacesApiService
): PlacesRepository {

    override suspend fun getRestaurants(
        lat: String,
        lon: String,
        keywords: List<String>
    ): List<Restaurant> {
        TODO("Not yet implemented")
    }

    override suspend fun getCityByName(cityNames: List<String>): List<City> {
        TODO("Not yet implemented")
    }

    override suspend fun getLatLonOfCity(id: String): Location {
        TODO("Not yet implemented")
    }
}