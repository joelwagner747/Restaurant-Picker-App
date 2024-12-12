package com.example.restaurantpickerapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpickerapp.data.models.RestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(restaurant: RestaurantEntity)

    @Delete
    suspend fun delete(restaurant: RestaurantEntity)

    @Query("SELECT * FROM restaurants ORDER BY name ASC")
    fun getAllRestaurants(): Flow<List<RestaurantEntity>>
}