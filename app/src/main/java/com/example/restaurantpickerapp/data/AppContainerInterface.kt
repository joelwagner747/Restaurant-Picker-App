package com.example.restaurantpickerapp.data

interface AppContainerInterface {
    val placesRepository: PlacesRepository

    val selectionOptionsRepository: SelectionOptionsRepositoryInterface
}