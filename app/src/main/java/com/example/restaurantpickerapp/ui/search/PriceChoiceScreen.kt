package com.example.restaurantpickerapp.ui.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.restaurantpickerapp.RestaurantPickerTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceChoiceScreen(
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier,
) {
    val searchCityUiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            RestaurantPickerTopAppBar(
                title = stringResource(SearchCityDestination.titleResource),
                canNavigateBack = false,
            )
        }
    ) { innerPadding ->
        PriceChoiceBody(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun PriceChoiceBody(
    modifier: Modifier = Modifier
) {

}