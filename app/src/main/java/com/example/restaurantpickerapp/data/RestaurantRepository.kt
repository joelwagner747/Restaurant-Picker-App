package com.example.restaurantpickerapp.data

import com.example.restaurantpickerapp.data.models.Restaurant
import com.example.restaurantpickerapp.data.models.RestaurantEntity
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {

    fun getAllRestaurantStream(): Flow<List<RestaurantEntity>>

    suspend fun insertItem(restaurant: RestaurantEntity)

    suspend fun deleteItem(restaurant: RestaurantEntity)
}