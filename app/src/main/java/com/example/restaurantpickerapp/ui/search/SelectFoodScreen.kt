package com.example.restaurantpickerapp.ui.search

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import com.example.restaurantpickerapp.R
import com.example.restaurantpickerapp.RestaurantPickerTopAppBar
import com.example.restaurantpickerapp.ui.navigation.NavigationDestination
import com.example.restaurantpickerapp.ui.search.model.FoodSearchType

object SelectFoodDestination : NavigationDestination {
    override val route = "SelectFood"
    override val titleResource = R.string.what_are_you_in_the_mood_for
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectFoodScreen(
    viewModel: SearchViewModel,
    navigateBack: () -> Unit,
    navigateToNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val searchUiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            RestaurantPickerTopAppBar(
                title = stringResource(SelectFoodDestination.titleResource),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        val foodTypeList = when(searchUiState.meal) {
            stringResource(R.string.breakfast) -> viewModel.getBreakfastFoodTypeOptions()
            stringResource(R.string.lunch) -> viewModel.getLunchFoodTypeOptions()
            stringResource(R.string.dinner) -> viewModel.getDinnerFoodTypeOptions()
            stringResource(R.string.dessert) -> viewModel.getDessertFoodTypeOptions()
            else -> listOf()
        }
        SelectFoodBody(
            currentFoodSearchType = searchUiState.currentFoodSearchType,
            setFoodSearchType = { viewModel.setCurrentFoodSearchType(it) },
            foodTypeList = foodTypeList,
            regionList = viewModel.getRegionOptions(),
            keywords = searchUiState.keywords,
            changeKeyWord = { viewModel.changeKeyword(it) },
            currentMeal = searchUiState.meal,
            navigateToNext = navigateToNext,
            searchForRestaurants = {
                if (searchUiState.meal != null && searchUiState.selectedCity != null && searchUiState.keywords.isNotEmpty() && searchUiState.price != null) {
                    viewModel.setSearchedRestaurantStateLoading()
                    viewModel.searchForRestaurants()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun SelectFoodBody(
    currentFoodSearchType: FoodSearchType,
    setFoodSearchType: (FoodSearchType) -> Unit,
    foodTypeList: List<Int>,
    regionList: List<Int>,
    keywords: Set<String>,
    changeKeyWord: (String) -> Unit,
    currentMeal: String?,
    navigateToNext: () -> Unit,
    searchForRestaurants: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sortedFoodTypeList = foodTypeList.map { foodTypeRef ->
        stringResource(foodTypeRef)
    }.sorted()
    val sortedRegionList = regionList.map { foodTypeRef ->
        stringResource(foodTypeRef)
    }.sorted()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        if (currentMeal != stringResource(R.string.breakfast) && currentMeal != stringResource(R.string.dessert)) {
            SelectByType(
                currentFoodSearchType = currentFoodSearchType,
                setFoodSearchType = setFoodSearchType
            )
        }
        if (currentFoodSearchType == FoodSearchType.BY_FOOD_TYPE || currentMeal == stringResource(R.string.breakfast) || currentMeal == stringResource(R.string.dessert)) {
            LazyColumn(
                modifier = Modifier
            ) {
                items(items = sortedFoodTypeList) { food ->
                    FoodItem(
                        food = food,
                        keywords = keywords,
                        changeKeyWord = changeKeyWord
                    )
                 }
            }
        } else if (currentFoodSearchType == FoodSearchType.BY_REGION && currentMeal != stringResource(R.string.breakfast) && currentMeal != stringResource(R.string.dessert)) {
            LazyColumn(
                modifier = Modifier
            ) {
                items(items = sortedRegionList) { food ->
                    FoodItem(
                        food = food,
                        keywords = keywords,
                        changeKeyWord = changeKeyWord
                    )
                }
            }
        }
        Button(
            onClick = {
                searchForRestaurants()
                navigateToNext()
            },
            enabled = keywords.isNotEmpty()
        ) {
            Text(stringResource(R.string.search))
        }

    }
}

@Composable
fun FoodItem(
    food: String,
    keywords: Set<String>,
    changeKeyWord: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(true, onClick = { changeKeyWord(food) }),
        colors = CardDefaults.cardColors(containerColor = if (keywords.contains(food)) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background)
    ) {
        Text(food)
    }
}

@Composable
fun SelectByType(
    currentFoodSearchType: FoodSearchType,
    setFoodSearchType: (FoodSearchType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = modifier.clickable(true, onClick = { setFoodSearchType(FoodSearchType.BY_FOOD_TYPE) }),
            colors = CardDefaults.cardColors(containerColor = if (currentFoodSearchType.value == FoodSearchType.BY_FOOD_TYPE.value) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background)
        ) {
            Text(stringResource(FoodSearchType.BY_FOOD_TYPE.value))
        }
        Card(
            modifier = modifier.clickable(true, onClick = {setFoodSearchType(FoodSearchType.BY_REGION) }),
            colors = CardDefaults.cardColors(containerColor = if (currentFoodSearchType.value == FoodSearchType.BY_REGION.value) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background)
        ) {
            Text(stringResource(FoodSearchType.BY_REGION.value))
        }
    }
}
