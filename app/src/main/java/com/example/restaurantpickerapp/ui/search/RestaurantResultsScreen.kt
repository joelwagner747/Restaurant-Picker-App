package com.example.restaurantpickerapp.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.restaurantpickerapp.BottomBar
import com.example.restaurantpickerapp.R
import com.example.restaurantpickerapp.RestaurantPickerTopAppBar
import com.example.restaurantpickerapp.data.models.Price
import com.example.restaurantpickerapp.data.models.Restaurant
import com.example.restaurantpickerapp.ui.navigation.NavigationDestination
import com.example.restaurantpickerapp.ui.search.model.PossibleCityState
import com.example.restaurantpickerapp.ui.search.model.SearchedRestaurantsState

object RestaurantResultsDestination : NavigationDestination {
    override val route = "RestaurantResults"
    override val titleResource = R.string.here_is_where_you_can_go
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantResultsScreen(
    viewModel: SearchViewModel,
    navigateBack: () -> Unit,
    onHomeButtonClicked: () -> Unit,
    onFavoriteButtonClicked: () -> Unit,
    onSearchButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val searchCityUiState by viewModel.uiState.collectAsState()
    val pricesMap: Map<Int, Price> = mapOf(
        1 to Price.ONE,
        2 to Price.TWO,
        3 to Price.THREE,
        4 to Price.FOUR
    )
    Scaffold(
        topBar = {
            RestaurantPickerTopAppBar(
                title = stringResource(RestaurantResultsDestination.titleResource),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomBar(
                onHomeButtonClicked = onHomeButtonClicked,
                onSearchButtonClicked = onSearchButtonClicked,
                onFavoriteButtonClicked = onFavoriteButtonClicked
            )
        }
    ) { innerPadding ->
        RestaurantResultsBody(
            restaurantResults = searchCityUiState.searchedRestaurants,
            pricesMap = pricesMap,
            retryAction = { viewModel.searchForRestaurants() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun RestaurantResultsBody(
    restaurantResults: SearchedRestaurantsState,
    pricesMap: Map<Int, Price>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        when(restaurantResults) {
            is SearchedRestaurantsState.Success -> DisplayRestaurantResults(
                restaurantResults = restaurantResults.searchedRestaurants,
                pricesMap = pricesMap
            )
            is SearchedRestaurantsState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is SearchedRestaurantsState.Error -> ErrorScreenRestaurant(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
    }

}

@Composable
fun DisplayRestaurantResults(
    restaurantResults: List<Restaurant>,
    pricesMap: Map<Int, Price>,
    modifier: Modifier = Modifier
) {
    if (restaurantResults.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
        ) {
            items(items = restaurantResults) { restaurant ->
                RestaurantItem(
                    restaurant = restaurant,
                    pricesMap = pricesMap,
                )
            }
        }
    } else {
        Text(stringResource(R.string.your_search_did_not_yield_any_results))
    }
}

@Composable
fun ErrorScreenRestaurant(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.search_was_unable_to_connect), modifier = Modifier.padding(16.dp))
    }
    Button(
        onClick = retryAction
    ) {
        Text(stringResource(R.string.retry))
    }
}

@Composable
fun RestaurantItem(
    restaurant: Restaurant,
    pricesMap: Map<Int, Price>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(restaurant.name ?: "Not available")
            Text(restaurant.keyword ?: "Not available")
            Text("Rating: ${restaurant.rating}")
            Text("Number of Reviews: ${restaurant.numOfUserRatings}")
            Text("Price Range: ${pricesMap[restaurant.priceLevel]?.priceRange ?: "Unknown"}")
            Text(if (restaurant.openHours?.openNow == true) "Open Now" else "Closed")
            Text(restaurant.address ?: "Not available")
        }
    }
}
