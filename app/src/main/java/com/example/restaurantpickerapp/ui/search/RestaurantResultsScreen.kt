package com.example.restaurantpickerapp.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.restaurantpickerapp.BottomBar
import com.example.restaurantpickerapp.BuildConfig
import com.example.restaurantpickerapp.R
import com.example.restaurantpickerapp.RestaurantPickerTopAppBar
import com.example.restaurantpickerapp.data.models.Price
import com.example.restaurantpickerapp.data.models.Restaurant
import com.example.restaurantpickerapp.data.models.RestaurantEntity
import com.example.restaurantpickerapp.ui.favorite.PhotoUrl
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
    val favoritesUiState by viewModel.favoritesUiState.collectAsState()
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
            addToFavorites = { viewModel.addToFavorites(it) },
            favoritesIdsSet = favoritesUiState.toSet(),
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun RestaurantResultsBody(
    restaurantResults: SearchedRestaurantsState,
    pricesMap: Map<Int, Price>,
    retryAction: () -> Unit,
    addToFavorites: (Restaurant) -> Unit,
    favoritesIdsSet: Set<String>,
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
                pricesMap = pricesMap,
                addToFavorites = addToFavorites,
                favoritesIdsSet = favoritesIdsSet
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
    addToFavorites: (Restaurant) -> Unit,
    favoritesIdsSet: Set<String>,
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
                    favoritesIdsSet = favoritesIdsSet,
                    addToFavorites = addToFavorites
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
fun RestaurantItemOld(
    restaurant: Restaurant,
    pricesMap: Map<Int, Price>,
    addToFavorites: (Restaurant) -> Unit,
    favoritesIdsSet: Set<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (!favoritesIdsSet.contains(restaurant.id)) {
                Button(
                    onClick = { addToFavorites(restaurant) }
                ) {
                    Text(stringResource(R.string.add_to_favorites))
                }
            } else {
                Text("Favorite")
            }
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

@Composable
fun RestaurantItem(
    restaurant: Restaurant,
    pricesMap: Map<Int, Price>,
    addToFavorites: (Restaurant) -> Unit,
    favoritesIdsSet: Set<String>,
    modifier: Modifier = Modifier
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(3f).padding(dimensionResource(R.dimen.padding_small)).fillMaxHeight()
            ) {
                if (restaurant.photos?.get(0)?.photoReference != "" && restaurant.photos?.get(0)?.width != 0) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data("${PhotoUrl.baseUrl}${restaurant.photos?.get(0)?.width}&photo_reference=${restaurant.photos?.get(0)?.photoReference}&key=${BuildConfig.Google_Places_API_KEY}")
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        modifier = Modifier.size(height = 140.dp, width = 150.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.backupfoodimage),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.size(height = 130.dp, width = 150.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small),
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium)
                        )
                ) {
                    if (!favoritesIdsSet.contains(restaurant.id)) {
                        Button(
                            onClick = { addToFavorites(restaurant) },
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier
                        ) {
                            Text(stringResource(R.string.add_to_favorites))
                        }
                    } else {
                        Column {
                            Spacer(Modifier.height(25.dp))
                            Row {
                                Icon(Icons.Outlined.Favorite, contentDescription = null)
                                Text("Favorite", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.weight(4f).fillMaxHeight().padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(restaurant.name ?: "Not available", style = MaterialTheme.typography.labelMedium)
                Spacer(Modifier.height(25.dp))
                Text(restaurant.keyword ?: "Not available")
                Spacer(Modifier.height(10.dp))
                Text("Rating: ${restaurant.rating}")
                Spacer(Modifier.height(10.dp))
                Text("${restaurant.numOfUserRatings} Reviews")
                Spacer(Modifier.height(10.dp))
                Text("Price Range: ${pricesMap[restaurant.priceLevel]?.priceRange ?: "Unknown"}")
                Spacer(Modifier.height(10.dp))
                Text(restaurant.address ?: "Not available")
                Spacer(Modifier.height(10.dp))
                Text(if (restaurant.openHours?.openNow == true) "Open Now" else "Closed")
            }

        }

    }
}
