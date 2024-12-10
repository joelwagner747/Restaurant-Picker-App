package com.example.restaurantpickerapp.data

import com.example.restaurantpickerapp.data.models.City
import com.example.restaurantpickerapp.data.models.Location
import com.example.restaurantpickerapp.data.models.Restaurant
import com.example.restaurantpickerapp.network.GooglePlacesApiService

class GooglePlacesRepository(
    private val googlePlacesApiService: GooglePlacesApiService,
    private val apiKey: String
): PlacesRepository {

    override suspend fun getRestaurants(
        lat: String,
        lon: String,
        keywords: List<String>
    ): List<Restaurant> {
        val restaurantMap: MutableMap<String, Restaurant> = mutableMapOf<String, Restaurant>()
        keywords.forEach { keyword ->
            val listOfResults = googlePlacesApiService.getRestaurants(
                location = "$lat,$lon",
                keyword = keyword,
                apiKey = apiKey
            )
            listOfResults.forEach { result: Restaurant ->
                result.keyword = keyword
                restaurantMap[result.id] = result
            }
        }

        return restaurantMap.values.toList()
    }

    override suspend fun getCityByName(cityName: String): List<City> {
        return googlePlacesApiService.getCity(city = cityName, apiKey = apiKey)
    }

    override suspend fun getLatLonOfCity(id: String): Location {
        return googlePlacesApiService.getCityLatLong(id = id, apiKey = apiKey)
    }
}