package com.example.restaurantpickerapp.data

import com.example.restaurantpickerapp.data.models.Price

interface SelectionOptionsRepositoryInterface {

    fun getMealOptions() : List<Int>

    fun getRegionOptions() : List<Int>

    fun getBreakfastFoodTypeOptions() : List<Int>

    fun getLunchFoodTypeOptions() : List<Int>

    fun getDinnerFoodTypeOptions() : List<Int>

    fun getDessertFoodTypeOptions() : List<Int>

    fun getPriceChoices() : List<Price>
}