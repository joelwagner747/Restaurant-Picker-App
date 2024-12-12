package com.example.restaurantpickerapp.ui.search.model

import com.example.restaurantpickerapp.data.models.City
import com.example.restaurantpickerapp.data.models.Location
import com.example.restaurantpickerapp.data.models.Restaurant

sealed interface SearchedRestaurantsState {
    data class Success(val searchedRestaurants: List<Restaurant>) : SearchedRestaurantsState
    object Error: SearchedRestaurantsState
    object Loading: SearchedRestaurantsState
}

sealed interface PossibleCityState {
    data class Success(val possibleCities: List<City>) : PossibleCityState
    object Error : PossibleCityState
    object Loading : PossibleCityState
}


enum class Price(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
}

data class SearchUiState(
    val keywords: Set<String>,
    val meal: String? = null,
    val price: Price? = null,
    val selectedCity: City? = null,
    val cityName: String,
    val state: String,
    var searchStarted: Boolean = false,
    val possibleCities: PossibleCityState = PossibleCityState.Loading,
    val searchedRestaurants: SearchedRestaurantsState = SearchedRestaurantsState.Loading
)
