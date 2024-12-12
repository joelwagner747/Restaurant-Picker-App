package com.example.restaurantpickerapp.data.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.restaurantpickerapp.data.RestaurantDao

@Database(entities = [RestaurantEntity::class], version = 2, exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao

    companion object {
        @Volatile
        private var Instance: RestaurantDatabase? = null

        fun getDatabase(context: Context): RestaurantDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RestaurantDatabase::class.java, "restaurant_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}