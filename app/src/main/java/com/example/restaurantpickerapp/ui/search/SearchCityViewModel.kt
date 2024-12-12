package com.example.restaurantpickerapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantpickerapp.data.PlacesRepository
import com.example.restaurantpickerapp.ui.search.model.PossibleCityState
import com.example.restaurantpickerapp.ui.search.model.SearchCityUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SearchCityViewModel(
    private val googlePlacesRepository: PlacesRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(SearchCityUiState(
        cityName = "",
        state = ""
    ))
    val uiState: StateFlow<SearchCityUiState> = _uiState.asStateFlow()

    fun changeCityName(cityName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                cityName = cityName
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

    fun changeState(state: String) {
        _uiState.update { currentState ->
            currentState.copy(
                state = state
            )
        }
    }
}