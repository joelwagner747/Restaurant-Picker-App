package com.example.restaurantpickerapp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.restaurantpickerapp.RestaurantPickerApplication
import com.example.restaurantpickerapp.ui.favorite.FavoritesViewModel
import com.example.restaurantpickerapp.ui.search.SearchViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SearchViewModel(
                googlePlacesRepository = restaurantPickerApplication().container.placesRepository,
                selectionOptionsRepository = restaurantPickerApplication().container.selectionOptionsRepository,
                restaurantsRepository = restaurantPickerApplication().container.restaurantRepository
            )
        }

        initializer {
            FavoritesViewModel(
                restaurantsRepository = restaurantPickerApplication().container.restaurantRepository
            )
        }
    }
}

fun CreationExtras.restaurantPickerApplication(): RestaurantPickerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as RestaurantPickerApplication)