package com.example.restaurantpickerapp.network

import com.example.restaurantpickerapp.data.models.City
import com.example.restaurantpickerapp.data.models.CitySuggestionsResponse
import com.example.restaurantpickerapp.data.models.Location
import com.example.restaurantpickerapp.data.models.LocationResponse
import com.example.restaurantpickerapp.data.models.Restaurant
import com.example.restaurantpickerapp.data.models.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApiService {
    @GET("nearbysearch/json")
    suspend fun getRestaurants(
        @Query("location") location: String,
        @Query("radius") radius: Int = 5000,
        @Query("type") type: String = "restaurant",
        @Query("keyword") keyword: String,
        //@Query("opennow") openNow: Boolean = true,
        @Query("key") apiKey: String,
    ): RestaurantResponse

    @GET("autocomplete/json")
    suspend fun getCity(
        @Query("input") cityAndState: String,
        @Query("types") type: String = "(cities)",
        @Query("components") components: String = "country:us",
        @Query("key") apiKey: String
    ) : CitySuggestionsResponse

    @GET("details/json")
    suspend fun getCityLatLong(
        @Query("place_id") id: String,
        @Query("fields") fields: String = "geometry",
        @Query("key") apiKey: String
    ) : LocationResponse
}