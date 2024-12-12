package com.example.restaurantpickerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.restaurantpickerapp.ui.AppViewModelProvider
import com.example.restaurantpickerapp.ui.search.PriceChoiceDestination
import com.example.restaurantpickerapp.ui.search.PriceChoiceScreen
import com.example.restaurantpickerapp.ui.search.RestaurantResultsDestination
import com.example.restaurantpickerapp.ui.search.RestaurantResultsScreen
import com.example.restaurantpickerapp.ui.search.SearchCityDestination
import com.example.restaurantpickerapp.ui.search.SearchCityScreen
import com.example.restaurantpickerapp.ui.search.SearchViewModel
import com.example.restaurantpickerapp.ui.search.SelectFoodDestination
import com.example.restaurantpickerapp.ui.search.SelectFoodScreen
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
                    navigateToPriceChoice = { navController.navigate(PriceChoiceDestination.route) }
                )
            }
            composable(PriceChoiceDestination.route) {
                PriceChoiceScreen(
                    viewModel = searchViewModel,
                    navigateBack = { navController.popBackStack() },
                    navigateToSelectMeal = { navController.navigate(SelectMealDestination.route) }
                )
            }
            composable(SelectMealDestination.route) {
                SelectMealScreen(
                    navigateBack = { navController.popBackStack() },
                    viewModel = searchViewModel,
                    navigateToNext = { navController.navigate(SelectFoodDestination.route) }
                )
            }
            composable(SelectFoodDestination.route) {
                SelectFoodScreen(
                    viewModel = searchViewModel,
                    navigateBack = { navController.popBackStack() },
                    navigateToNext = { navController.navigate(RestaurantResultsDestination.route) }
                )
            }
            composable(RestaurantResultsDestination.route) {
                RestaurantResultsScreen(
                    viewModel = searchViewModel,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }

    }
}