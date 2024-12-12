package com.example.restaurantpickerapp.data

import com.example.restaurantpickerapp.data.models.City
import com.example.restaurantpickerapp.data.models.Location
import com.example.restaurantpickerapp.data.models.Price
import com.example.restaurantpickerapp.data.models.Restaurant
import com.example.restaurantpickerapp.network.GooglePlacesApiService

class GooglePlacesRepository(
    private val googlePlacesApiService: GooglePlacesApiService,
    private val apiKey: String
): PlacesRepository {

    override suspend fun getRestaurants(
        cityId: String,
        meal: String,
        keywords: List<String>,
        price: Price
    ): List<Restaurant> {
        val location = googlePlacesApiService.getCityLatLong(
            id = cityId,
            apiKey = apiKey
        ).result?.geometry?.location
        val restaurantMap: MutableMap<String, Restaurant> = mutableMapOf<String, Restaurant>()
        if (location != null) {
            keywords.forEach { keyword ->
                val apiResponse = googlePlacesApiService.getRestaurants(
                    location = "${location.lat},${location.lng}",
                    keyword = "$meal+$keyword",
                    apiKey = apiKey
                )
                val listOfResults = apiResponse.results
                val nextPageToken: String? = apiResponse.nextPageToken
                listOfResults.forEach { result: Restaurant ->
                    if (result.id != null && result.name != null && result.address != null) {
                        if (result.openHours?.openNow == true && result.priceLevel == price.value && result.businessStatus == "OPERATIONAL") {
                            result.keyword = keyword
                            restaurantMap[result.id] = result
                        }
                    }
                }
            }
        }

        return restaurantMap.values.toList()
    }

    override suspend fun getCityByName(cityName: String, state: String): List<City> {
        return googlePlacesApiService.getCity(cityAndState = "$cityName+$state", apiKey = apiKey).predictions?.filter { it.placeId != null && it.name != null } ?: listOf()
    }
}