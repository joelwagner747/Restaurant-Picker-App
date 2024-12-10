package com.example.restaurantpickerapp.network

import com.example.restaurantpickerapp.data.City
import com.example.restaurantpickerapp.data.Location
import com.example.restaurantpickerapp.data.Restaurant
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApiService {
    @GET("nearbysearch/json")
    suspend fun getRestaurants(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "restaurant",
        @Query("keyword") keyword: String,
        @Query("key") apiKey: String,
    ): List<Restaurant>

    @GET("autocomplete/json")
    suspend fun getCity(
        @Query("input") city: String,
        @Query("types") type: String = "(cities)",
        @Query("components") components: String = "country:us",
        @Query("key") apiKey: String
    ) : List<City>

    @GET("details/json")
    suspend fun getCityLatLong(
        @Query("place_id") id: String,
        @Query("fields") fields: String,
        @Query("key") apiKey: String
    ) : List<Location>
}