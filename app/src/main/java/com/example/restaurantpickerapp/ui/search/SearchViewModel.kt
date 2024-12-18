package com.example.restaurantpickerapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantpickerapp.data.PlacesRepository
import com.example.restaurantpickerapp.data.RestaurantRepository
import com.example.restaurantpickerapp.data.SelectionOptionsRepositoryInterface
import com.example.restaurantpickerapp.data.models.City
import com.example.restaurantpickerapp.data.models.Price
import com.example.restaurantpickerapp.data.models.Restaurant
import com.example.restaurantpickerapp.data.models.RestaurantEntity
import com.example.restaurantpickerapp.ui.search.model.FoodSearchType
import com.example.restaurantpickerapp.ui.search.model.PossibleCityState
import com.example.restaurantpickerapp.ui.search.model.SearchUiState
import com.example.restaurantpickerapp.ui.search.model.SearchedRestaurantsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException


class SearchViewModel(
    private val googlePlacesRepository: PlacesRepository,
    private val selectionOptionsRepository: SelectionOptionsRepositoryInterface,
    private val restaurantsRepository: RestaurantRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState(
        keywords = mutableSetOf<String>(),
        cityName = "",
        state = ""
    ))
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    val favoritesUiState: StateFlow<List<String>> = restaurantsRepository.getAllRestaurantsIdsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )

    fun changeCityName(cityName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                cityName = cityName
            )
        }
    }

    fun changeState(state: String) {
        _uiState.update { currentState ->
            currentState.copy(
                state = state
            )
        }
    }

    fun changeKeyword(keyword: String) {
        _uiState.update { currentState ->
            val mutableKeywordsSet: MutableSet<String> = currentState.keywords.toMutableSet()
            if (mutableKeywordsSet.contains(keyword)) {
                mutableKeywordsSet.remove(keyword)
            } else {
                mutableKeywordsSet.add(keyword)
            }
            currentState.copy(
                keywords = mutableKeywordsSet.toSet()
            )
        }
    }

    fun setMeal(meal: String) {
        _uiState.update { currentState ->
            currentState.copy(
                meal = meal,
                keywords = setOf()
            )
        }
    }

    fun selectCity(city: City) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCity = city
            )
        }
    }

    fun clearCity() {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCity = null
            )
        }
    }

    fun setPrice(price: Price) {
        _uiState.update { currentState ->
            currentState.copy(
                price = price
            )
        }
    }

    fun setCurrentFoodSearchType(newFoodSearchType: FoodSearchType) {
        _uiState.update { currentState ->
            currentState.copy(
                currentFoodSearchType = newFoodSearchType
            )
        }
    }

    fun clearSearch() {
        _uiState.update {
            SearchUiState(
                keywords = mutableSetOf<String>(),
                cityName = "",
                state = ""
            )
        }
    }

    fun searchForCitiesByName() {
        if (_uiState.value.cityName != "" || _uiState.value.state != "") {
            _uiState.update { currentState ->
                currentState.copy(
                    searchStarted = true
                )
            }
            viewModelScope.launch {
                _uiState.update { currentState ->
                    currentState.copy(
                        possibleCities = try {
                            PossibleCityState.Success(
                                googlePlacesRepository.getCityByName(
                                    cityName = currentState.cityName,
                                    state = currentState.state
                                )
                            )
                        } catch (e: IOException) {
                            PossibleCityState.Error
                        }
                    )
                }
            }
        }
    }

    fun setSearchedRestaurantStateLoading() {
        _uiState.update { currentState ->
            currentState.copy(
                searchedRestaurants = SearchedRestaurantsState.Loading
            )
        }
    }

    fun searchForRestaurants() {
        if (_uiState.value.selectedCity != null && _uiState.value.meal != null && _uiState.value.keywords.isNotEmpty() && _uiState.value.price != null && _uiState.value.selectedCity?.placeId != null) {
            viewModelScope.launch {
                _uiState.update { currentState ->
                    if (currentState.selectedCity != null && currentState.meal != null && currentState.price != null && currentState.selectedCity.placeId != null) {
                        currentState.copy(
                            searchedRestaurants = try {
                                SearchedRestaurantsState.Success(
                                    googlePlacesRepository.getRestaurants(
                                        cityId = currentState.selectedCity.placeId,
                                        meal = currentState.meal,
                                        keywords = currentState.keywords.toList(),
                                        price = currentState.price
                                    )
                                )
                            } catch (e: IOException) {
                                SearchedRestaurantsState.Error
                            }
                        )
                    } else {
                        currentState
                    }
                }
            }
        }
    }

    fun addToFavorites(restaurant: Restaurant) {
        if (validateRestaurant(restaurant)) {
            val restaurantEntity = convertRestaurantToRestaurantEntity(restaurant)
            viewModelScope.launch {
                restaurantsRepository.insertItem(restaurantEntity)
            }
        }
    }

    fun getMealsOptions() : List<Int> {
        return selectionOptionsRepository.getMealOptions()
    }

    fun getRegionOptions() : List<Int> {
        return selectionOptionsRepository.getRegionOptions()
    }

    fun getBreakfastFoodTypeOptions() : List<Int> {
        return selectionOptionsRepository.getBreakfastFoodTypeOptions()
    }

    fun getLunchFoodTypeOptions() : List<Int> {
        return selectionOptionsRepository.getLunchFoodTypeOptions()
    }

    fun getDinnerFoodTypeOptions() : List<Int> {
        return selectionOptionsRepository.getDinnerFoodTypeOptions()
    }

    fun getDessertFoodTypeOptions() : List<Int> {
        return selectionOptionsRepository.getDessertFoodTypeOptions()
    }

    fun getPriceChoices() : List<Price> {
        return selectionOptionsRepository.getPriceChoices()
    }
}

fun convertRestaurantToRestaurantEntity(restaurant: Restaurant) : RestaurantEntity {
    return RestaurantEntity(
        id = restaurant.id ?: "",
        name = restaurant.name ?: "",
        priceLevel = restaurant.priceLevel,
        rating = restaurant.rating,
        numOfUserRatings = restaurant.numOfUserRatings,
        address = restaurant.address ?: "",
        photoReference = restaurant.photos?.get(0)?.photoReference ?: "",
        maxWidth = restaurant.photos?.get(0)?.width ?: 0
    )
}

fun validateRestaurant(restaurant: Restaurant) : Boolean {
    return restaurant.id != null && restaurant.name != null && restaurant.address != null
}