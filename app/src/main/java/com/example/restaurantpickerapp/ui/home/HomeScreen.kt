package com.example.restaurantpickerapp.ui.home

import androidx.annotation.XmlRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.restaurantpickerapp.BottomBar
import com.example.restaurantpickerapp.R
import com.example.restaurantpickerapp.RestaurantPickerTopAppBar
import com.example.restaurantpickerapp.ui.navigation.NavigationDestination
import com.example.restaurantpickerapp.ui.search.SearchCityBody
import com.example.restaurantpickerapp.ui.search.SearchCityDestination

object HomeDestination: NavigationDestination {
    override val route = "Home"
    override val titleResource = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onHomeButtonClicked: () -> Unit,
    onFavoriteButtonClicked: () -> Unit,
    onSearchButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            RestaurantPickerTopAppBar(
                title = stringResource(HomeDestination.titleResource),
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
        HomeBody(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeBody(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Spacer(Modifier.height(50.dp))
        Card(
            //modifier = modifier.padding(5.dp)
        ) {
            Text("Welcome to Eat Away", style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(25.dp))
        }
        Spacer(Modifier.height(50.dp))
        Card(
            //modifier = modifier.padding(5.dp)
        ) {
            Text("Your one stop app for choosing where to eat", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(15.dp))
        }
        Spacer(Modifier.height(50.dp))
        Card(
            //modifier = modifier.padding(5.dp)
        ) {
            Text(
                "Hit Search at the bottom to begin looking for restaurants or select favorites to view the places that you just love",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(15.dp)
            )
        }
        Spacer(Modifier.height(50.dp))
    }
}
