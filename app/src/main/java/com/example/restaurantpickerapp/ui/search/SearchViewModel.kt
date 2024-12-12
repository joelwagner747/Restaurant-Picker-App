package com.example.restaurantpickerapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantpickerapp.data.PlacesRepository
import com.example.restaurantpickerapp.data.SelectionOptionsRepositoryInterface
import com.example.restaurantpickerapp.data.models.City
import com.example.restaurantpickerapp.ui.search.model.PossibleCityState
import com.example.restaurantpickerapp.ui.search.model.SearchUiState
import com.example.restaurantpickerapp.ui.search.model.SearchedRestaurantsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException


class SearchViewModel(
    private val googlePlacesRepository: PlacesRepository,
    private val selectionOptionsRepository: SelectionOptionsRepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState(
        keywords = mutableSetOf<String>(),
        cityName = "",
        state = ""
    ))
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

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

    fun addKeyword(keyword: String) {
        _uiState.update { currentState ->
            val mutableKeywordsSet: MutableSet<String> = currentState.keywords.toMutableSet()
            mutableKeywordsSet.add(keyword)
            currentState.copy(
                keywords = mutableKeywordsSet.toSet()
            )
        }
    }

    fun removeKeyword(keyword: String) {
        _uiState.update { currentState ->
            val mutableKeywordsSet: MutableSet<String> = currentState.keywords.toMutableSet()
            mutableKeywordsSet.remove(keyword)
            currentState.copy(
                keywords = mutableKeywordsSet.toSet()
            )
        }
    }

    fun setMeal(meal: String) {
        _uiState.update { currentState ->
            currentState.copy(
                meal = meal
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

    fun searchForRestaurants() {
        if (_uiState.value.selectedCity != null && _uiState.value.meal != null && _uiState.value.keywords.isNotEmpty()) {
            viewModelScope.launch {
                _uiState.update { currentState ->
                    if (currentState.selectedCity != null && currentState.meal != null) {
                        currentState.copy(
                            searchedRestaurants = try {
                                SearchedRestaurantsState.Success(
                                    googlePlacesRepository.getRestaurants(
                                        cityId = currentState.selectedCity.placeId,
                                        meal = currentState.meal,
                                        keywords = currentState.keywords.toList()
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
}