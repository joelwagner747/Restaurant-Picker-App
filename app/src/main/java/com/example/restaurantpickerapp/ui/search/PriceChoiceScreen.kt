package com.example.restaurantpickerapp.ui.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.restaurantpickerapp.BottomBar
import com.example.restaurantpickerapp.R
import com.example.restaurantpickerapp.RestaurantPickerTopAppBar
import com.example.restaurantpickerapp.data.models.Price
import com.example.restaurantpickerapp.ui.navigation.NavigationDestination

object PriceChoiceDestination : NavigationDestination {
    override val route = "ChoosePrice"
    override val titleResource = R.string.how_much_do_you_want_to_spend
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceChoiceScreen(
    viewModel: SearchViewModel,
    navigateBack: () -> Unit,
    navigateToSelectMeal: () -> Unit,
    onHomeButtonClicked: () -> Unit,
    onFavoriteButtonClicked: () -> Unit,
    onSearchButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val searchCityUiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            RestaurantPickerTopAppBar(
                title = stringResource(PriceChoiceDestination.titleResource),
                canNavigateBack = true,
                navigateUp = navigateBack
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
        PriceChoiceBody(
            priceChoicesList = viewModel.getPriceChoices(),
            selectedPrice = searchCityUiState.price,
            onPriceSelected = { viewModel.setPrice(it) },
            navigateToSelectMeal = navigateToSelectMeal,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun PriceChoiceBody(
    priceChoicesList: List<Price>,
    selectedPrice: Price?,
    onPriceSelected: (Price) -> Unit,
    navigateToSelectMeal: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        priceChoicesList.forEach { priceChoice ->
            PriceOptionItem(
                price = priceChoice,
                selectedPrice = selectedPrice,
                onPriceSelected = onPriceSelected
            )
        }
        Button(
            onClick = {
                if (selectedPrice != null) {
                    navigateToSelectMeal()
                }
                      },
            enabled = selectedPrice != null,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(height = 60.dp, width = 150.dp)
        ) {
            Text(stringResource(R.string.select), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun PriceOptionItem(
    price: Price,
    selectedPrice: Price?,
    onPriceSelected: (Price) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(true, onClick = { onPriceSelected(price) })
            .padding(dimensionResource(R.dimen.padding_medium))
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(14.dp))
            .size(width = 250.dp, height = 50.dp),
        colors = CardDefaults.cardColors(containerColor = if (price.value == (selectedPrice?.value
                ?: - 1)
        ) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.outline)
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                price.priceRange,
                style = MaterialTheme.typography.displayMedium,
            )
        }
    }
}