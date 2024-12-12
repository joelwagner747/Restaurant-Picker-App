package com.example.restaurantpickerapp.data

import com.example.restaurantpickerapp.data.models.RestaurantEntity
import kotlinx.coroutines.flow.Flow

class OfflineRestaurantsRepository(
    private val restaurantDao: RestaurantDao
) : RestaurantRepository {
    override fun getAllRestaurantStream(): Flow<List<RestaurantEntity>> {
        return restaurantDao.getAllRestaurants()
    }

    override suspend fun deleteItem(restaurant: RestaurantEntity) {
        return restaurantDao.delete(restaurant)
    }

    override suspend fun insertItem(restaurant: RestaurantEntity) {
        return restaurantDao.insert(restaurant)
    }

    override fun getAllRestaurantsIdsStream(): Flow<List<String>> {
        return restaurantDao.getAllRestaurantIds()
    }
}