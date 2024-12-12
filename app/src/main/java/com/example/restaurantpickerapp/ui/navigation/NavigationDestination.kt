package com.example.restaurantpickerapp.ui.navigation

import androidx.annotation.StringRes

interface NavigationDestination {
    val route: String

    val titleResource: Int
}