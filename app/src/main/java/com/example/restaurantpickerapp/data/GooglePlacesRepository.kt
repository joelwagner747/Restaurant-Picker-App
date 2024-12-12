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
        cityId: String,
        meal: String,
        keywords: List<String>
    ): List<Restaurant> {
        val location = googlePlacesApiService.getCityLatLong(
            id = cityId,
            apiKey = apiKey
        )
        val restaurantMap: MutableMap<String, Restaurant> = mutableMapOf<String, Restaurant>()
        if (location != null) {
            keywords.forEach { keyword ->
                val listOfResults = googlePlacesApiService.getRestaurants(
                    location = "${location.lat},${location.lng}",
                    keyword = "$meal+$keyword",
                    apiKey = apiKey
                )
                listOfResults.forEach { result: Restaurant ->
                    if (result.openNow) {
                        result.keyword = keyword
                        restaurantMap[result.id] = result
                    }
                }
            }
        }

        return restaurantMap.values.toList()
    }

    override suspend fun getCityByName(cityName: String, state: String): List<City> {
        return googlePlacesApiService.getCity(cityAndState = "$cityName+$state", apiKey = apiKey).predictions
    }
}