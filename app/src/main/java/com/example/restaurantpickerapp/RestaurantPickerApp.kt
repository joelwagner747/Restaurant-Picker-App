@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.restaurantpickerapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.restaurantpickerapp.ui.navigation.RestaurantPickerNavHost

@Composable
fun RestaurantPickerApp(navController: NavHostController = rememberNavController()) {
    RestaurantPickerNavHost(navController = navController)
}

@Composable
fun RestaurantPickerTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun BottomBar(onHomeButtonClicked: () -> Unit, onFavoriteButtonClicked: () -> Unit, onSearchButtonClicked: () -> Unit, modifier: Modifier = Modifier) {
    BottomAppBar {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onSearchButtonClicked,
                modifier = Modifier.width(100.dp)

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    //modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(Icons.Outlined.Search, contentDescription = null)
                    Text(stringResource(R.string.search), fontSize = 10.sp)
                }
            }
            IconButton(
                onClick = onHomeButtonClicked,
                modifier = Modifier.width(100.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    //modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(Icons.Outlined.Home, contentDescription = null)
                    Text(stringResource(R.string.home), fontSize = 10.sp)
                }
            }
            IconButton(
                onClick = onFavoriteButtonClicked,
                modifier = Modifier.width(100.dp)

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    //modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(Icons.Outlined.Favorite, contentDescription = null)
                    Text(stringResource(R.string.favorites), fontSize = 10.sp)
                }
            }
        }
    }
}