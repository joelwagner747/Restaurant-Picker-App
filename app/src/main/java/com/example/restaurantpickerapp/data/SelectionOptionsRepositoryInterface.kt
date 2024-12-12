package com.example.restaurantpickerapp.data

interface SelectionOptionsRepositoryInterface {

    fun getMealOptions() : List<Int>

    fun getRegionOptions() : List<Int>

    fun getBreakfastFoodTypeOptions() : List<Int>

    fun getLunchFoodTypeOptions() : List<Int>

    fun getDinnerFoodTypeOptions() : List<Int>

    fun getDessertFoodTypeOptions() : List<Int>
}