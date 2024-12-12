package com.example.restaurantpickerapp.ui.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantpickerapp.BottomBar
import com.example.restaurantpickerapp.R
import com.example.restaurantpickerapp.RestaurantPickerTopAppBar
import com.example.restaurantpickerapp.data.models.Price
import com.example.restaurantpickerapp.data.models.RestaurantEntity
import com.example.restaurantpickerapp.ui.AppViewModelProvider
import com.example.restaurantpickerapp.ui.navigation.NavigationDestination
import com.example.restaurantpickerapp.ui.search.SearchCityBody
import com.example.restaurantpickerapp.ui.search.SearchCityDestination
import com.example.restaurantpickerapp.ui.search.SearchViewModel

object FavoritesDestination : NavigationDestination {
    override val route = "Favorites"
    override val titleResource = R.string.favorites
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onHomeButtonClicked: () -> Unit,
    onFavoriteButtonClicked: () -> Unit,
    onSearchButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
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
                title = stringResource(FavoritesDestination.titleResource),
                canNavigateBack = false,
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
        FavoritesBody(
            restaurantsList = favoritesUiState.restaurantList,
            pricesMap = pricesMap,
            onDelete = { viewModel.deleteFavorite(it) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FavoritesBody(
    restaurantsList: List<RestaurantEntity>,
    pricesMap: Map<Int, Price>,
    onDelete: (RestaurantEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(items = restaurantsList, key = { it.id }) { item ->
                FavoriteItem(
                    restaurant = item,
                    pricesMap = pricesMap,
                    onDelete = onDelete
                )
            }
        }
    }
}

@Composable
fun FavoriteItem(
    restaurant: RestaurantEntity,
    pricesMap: Map<Int, Price>,
    onDelete: (RestaurantEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = { deleteConfirmationRequired = true },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.delete))
            }
            Text(restaurant.name)
            Text("Rating: ${restaurant.rating}")
            Text("Number of Reviews: ${restaurant.numOfUserRatings}")
            Text("Price Range: ${pricesMap[restaurant.priceLevel]?.priceRange ?: "Unknown"}")
            Text(restaurant.address)

            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        onDelete(restaurant)
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes))
            }
        })
}
