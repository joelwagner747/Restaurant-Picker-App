package com.example.restaurantpickerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.restaurantpickerapp.ui.AppViewModelProvider
import com.example.restaurantpickerapp.ui.search.SearchCityDestination
import com.example.restaurantpickerapp.ui.search.SearchCityScreen
import com.example.restaurantpickerapp.ui.search.SearchViewModel
import com.example.restaurantpickerapp.ui.search.SelectMealDestination
import com.example.restaurantpickerapp.ui.search.SelectMealScreen

@Composable
fun RestaurantPickerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val searchViewModel: SearchViewModel = viewModel(
        factory = AppViewModelProvider.Factory,
    )

    NavHost(
        navController = navController,
        startDestination = "SearchRestaurants",
        modifier = modifier
    ) {
        navigation(startDestination = SearchCityDestination.route, route = "SearchRestaurants") {
            composable(SearchCityDestination.route) {
                SearchCityScreen(
                    viewModel = searchViewModel,
                    navigateToPriceChoice = { TODO() }
                )
            }
            composable(SelectMealDestination.route) {
                SelectMealScreen(
                    navigateBack = { navController.popBackStack() },
                    viewModel = searchViewModel,
                    onMealSelected = { mealName ->
                        searchViewModel.setMeal(mealName)
                        navController.navigate(SearchCityDestination.route)
                    }
                )
            }
        }

    }
}