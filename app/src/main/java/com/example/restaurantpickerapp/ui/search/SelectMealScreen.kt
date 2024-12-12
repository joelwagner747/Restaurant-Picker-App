package com.example.restaurantpickerapp.ui.search

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.restaurantpickerapp.R
import com.example.restaurantpickerapp.RestaurantPickerTopAppBar
import com.example.restaurantpickerapp.ui.navigation.NavigationDestination
import com.example.restaurantpickerapp.ui.search.model.SearchUiState

object SelectMealDestination : NavigationDestination {
    override val route = "SelectMeal"
    override val titleResource = R.string.pick_a_meal
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectMealScreen(
    viewModel: SearchViewModel,
    navigateBack: () -> Unit,
    onMealSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchUiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            RestaurantPickerTopAppBar(
                title = stringResource(SelectMealDestination.titleResource),
                canNavigateBack = true,
            )
        }
    ) { innerPadding ->
        SelectMealBody(
            mealTypesList = viewModel.getMealsOptions(),
            onButtonPressed = onMealSelected,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun SelectMealBody(
    mealTypesList: List<Int>,
    onButtonPressed: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        mealTypesList.forEach { mealName ->
            MealItem(
                mealName = mealName,
                onButtonPressed = onButtonPressed,
            )
        }
    }
}

@Composable
fun MealItem(
    @StringRes
    mealName: Int,
    onButtonPressed: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val meal = stringResource(mealName)
    Button(
        onClick = { onButtonPressed(meal) },
        modifier = modifier
    ) {
        Text(
            text = meal
        )
    }
}

@Preview
@Composable
fun SelectMealBodyPreview() {

}