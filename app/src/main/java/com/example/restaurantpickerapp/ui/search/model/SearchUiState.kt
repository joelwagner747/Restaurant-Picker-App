package com.example.restaurantpickerapp.ui.search.model

import com.example.restaurantpickerapp.R
import com.example.restaurantpickerapp.data.models.City
import com.example.restaurantpickerapp.data.models.Location
import com.example.restaurantpickerapp.data.models.Price
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

enum class FoodSearchType(val value: Int) {
    BY_FOOD_TYPE(R.string.by_food_type),
    BY_REGION(R.string.by_region)
}

data class SearchUiState(
    val keywords: Set<String>,
    val meal: String? = null,
    val price: Price? = null,
    val selectedCity: City? = null,
    val cityName: String,
    val state: String,
    val currentFoodSearchType: FoodSearchType = FoodSearchType.BY_FOOD_TYPE,
    var searchStarted: Boolean = false,
    val possibleCities: PossibleCityState = PossibleCityState.Loading,
    val searchedRestaurants: SearchedRestaurantsState = SearchedRestaurantsState.Loading
)
