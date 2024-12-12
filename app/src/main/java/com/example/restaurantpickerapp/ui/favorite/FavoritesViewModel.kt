package com.example.restaurantpickerapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantpickerapp.data.OfflineRestaurantsRepository
import com.example.restaurantpickerapp.data.RestaurantRepository
import com.example.restaurantpickerapp.data.models.RestaurantDatabase
import com.example.restaurantpickerapp.data.models.RestaurantEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val restaurantsRepository: RestaurantRepository
) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val favoritesUiState: StateFlow<FavoritesUiState> = restaurantsRepository.getAllRestaurantStream().map { FavoritesUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = FavoritesUiState()
        )

    fun deleteFavorite(restaurant: RestaurantEntity) {
        viewModelScope.launch {
            restaurantsRepository.deleteItem(restaurant)
        }
    }
}

data class FavoritesUiState(val restaurantList: List<RestaurantEntity> = listOf())