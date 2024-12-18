package com.example.restaurantpickerapp.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantpickerapp.BottomBar
import com.example.restaurantpickerapp.R
import com.example.restaurantpickerapp.RestaurantPickerTopAppBar
import com.example.restaurantpickerapp.data.models.City
import com.example.restaurantpickerapp.ui.AppViewModelProvider
import com.example.restaurantpickerapp.ui.navigation.NavigationDestination
import com.example.restaurantpickerapp.ui.search.model.PossibleCityState

object SearchCityDestination : NavigationDestination {
    override val route = "SelectCity"
    override val titleResource = R.string.search_for_a_location
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCityScreen(
    viewModel: SearchViewModel,
    navigateToPriceChoice: () -> Unit,
    onHomeButtonClicked: () -> Unit,
    onFavoriteButtonClicked: () -> Unit,
    onSearchButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val searchCityUiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            RestaurantPickerTopAppBar(
                title = stringResource(SearchCityDestination.titleResource),
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
        },
        floatingActionButton = {
            if (searchCityUiState.possibleCities is PossibleCityState.Success) {
                Button(
                    onClick = {
                        if (searchCityUiState.selectedCity != null) {
                            navigateToPriceChoice()
                        }
                    },
                    enabled = searchCityUiState.selectedCity != null,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(height = 60.dp, width = 150.dp)
                ) {
                    Text(
                        stringResource(R.string.select),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    ) { innerPadding ->
        SearchCityBody(
            cityName = searchCityUiState.cityName,
            state = searchCityUiState.state,
            onCityNameChanged = { viewModel.changeCityName(it) },
            onStateNameChanged = { viewModel.changeState(it) },
            searchStarted = searchCityUiState.searchStarted,
            possibleCities = searchCityUiState.possibleCities,
            onSearchStarted = { viewModel.searchForCitiesByName() },
            selectedCity = searchCityUiState.selectedCity,
            onCitySelected = { viewModel.selectCity(it) },
            navigateToPriceChoice = navigateToPriceChoice,
            clearCity = { viewModel.clearCity() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun SearchCityBody(
    cityName: String,
    state: String,
    onCityNameChanged: (String) -> Unit,
    onStateNameChanged: (String) -> Unit,
    searchStarted: Boolean,
    possibleCities: PossibleCityState,
    onSearchStarted: () -> Unit,
    selectedCity: City?,
    onCitySelected: (City) -> Unit,
    navigateToPriceChoice: () -> Unit,
    clearCity: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        SearchFields(
            cityName = cityName,
            state = state,
            onCityNameChanged = onCityNameChanged,
            onStateNameChanged = onStateNameChanged,
            onSearchStarted = onSearchStarted,
            clearCity = clearCity
        )
        if (searchStarted) {
            DisplaySearch(
                possibleCitiesState = possibleCities,
                selectedCity = selectedCity,
                onCitySelected = onCitySelected,
                navigateToPriceChoice = navigateToPriceChoice
            )
        }
    }
}

@Composable
fun SearchFields(
    cityName: String,
    state: String,
    onCityNameChanged: (String) -> Unit,
    onStateNameChanged: (String) -> Unit,
    onSearchStarted: () -> Unit,
    clearCity: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = cityName,
        onValueChange = { onCityNameChanged(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = { Text(stringResource(R.string.city_name))}
    )
    Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))
    OutlinedTextField(
        value = state,
        onValueChange = { onStateNameChanged(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = { Text(stringResource(R.string.state))}
    )
    Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))
    Button(
        onClick = {
            clearCity()
            onSearchStarted()
        },
        enabled = cityName != "" && state != "",
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.size(height = 60.dp, width = 150.dp)
    ) {
        Text(stringResource(R.string.search_for_city), style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun DisplaySearch(
    possibleCitiesState: PossibleCityState,
    selectedCity: City?,
    onCitySelected: (City) -> Unit,
    navigateToPriceChoice: () -> Unit,
    modifier: Modifier = Modifier
) {
    when(possibleCitiesState) {
        is PossibleCityState.Success -> DisplaySuccessfulSearchResults(
            possibleCitiesState.possibleCities,
            selectedCity,
            onCitySelected,
            navigateToPriceChoice
        )
        is PossibleCityState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PossibleCityState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun DisplaySuccessfulSearchResults(
    results: List<City>,
    selectedCity: City?,
    onCitySelected: (City) -> Unit,
    navigateToPriceChoice: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (results.isNotEmpty()) {

        LazyColumn(
            modifier = modifier
        ) {
            items(items = results) { result ->
                SearchResultItem(
                    city = result,
                    selectedCity = selectedCity,
                    onCitySelected = onCitySelected
                )
            }
            item {
                Spacer(Modifier.height(70.dp))
            }
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(300.dp).padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(stringResource(R.string.your_search_did_not_yield_any_results))
        }
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
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
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun SearchResultItem(
    city: City,
    selectedCity: City?,
    onCitySelected: (City) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(true, onClick = { onCitySelected(city) })
            .padding(dimensionResource(R.dimen.padding_medium))
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(14.dp))
            .size(width = 250.dp, height = 50.dp),
        colors = CardDefaults.cardColors(containerColor = if (city.placeId == (selectedCity?.placeId
                ?: "")
        ) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.outline)
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(city.name ?: "Unknown", style = MaterialTheme.typography.displayMedium)
        }
    }
}