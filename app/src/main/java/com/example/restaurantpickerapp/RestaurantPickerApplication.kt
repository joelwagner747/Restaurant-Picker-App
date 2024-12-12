package com.example.restaurantpickerapp

import android.app.Application
import com.example.restaurantpickerapp.data.AppContainerInterface
import com.example.restaurantpickerapp.data.DefaultAppContainer

class RestaurantPickerApplication: Application() {
    lateinit var container: AppContainerInterface
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}