package com.example.restaurantpickerapp.data

import com.example.restaurantpickerapp.BuildConfig
import com.example.restaurantpickerapp.network.GooglePlacesApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class DefaultAppContainer : AppContainerInterface {

    private val baseUrl = "https://maps.googleapis.com/maps/api/place/"

    private val apiKey = BuildConfig.Google_Places_API_KEY

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("applications/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: GooglePlacesApiService by lazy {
        retrofit.create(GooglePlacesApiService::class.java)
    }

    override val placesRepository: PlacesRepository by lazy {
        GooglePlacesRepository(retrofitService, apiKey)
    }
}