package com.example.restaurantpickerapp.ui.search.model

import com.example.restaurantpickerapp.data.models.City


data class SearchCityUiState(
    val cityName: String,
    val state: String,
    var searchStarted: Boolean = false,
    val possibleCities: PossibleCityState = PossibleCityState.Loading
)
