package com.example.restaurantpickerapp.data

import com.example.restaurantpickerapp.R
import com.example.restaurantpickerapp.data.models.Price

class SelectionOptionsRepository : SelectionOptionsRepositoryInterface {

    override fun getBreakfastFoodTypeOptions(): List<Int> {
        return listOf(
            R.string.coffee,
            R.string.baked_goods,
            R.string.traditional_breakfast,
        )
    }

    override fun getLunchFoodTypeOptions(): List<Int> {
        return listOf(
            R.string.pizza,
            R.string.pasta,
            R.string.burgers,
            R.string.steak,
            R.string.barbeque,
            R.string.soup,
            R.string.salad,
            R.string.sushi,
            R.string.sandwiches,
            R.string.tacos,
            R.string.burritos,
            R.string.bowls,
            R.string.noodles,
            R.string.ramen,
            R.string.wings,
            R.string.nachos,
            R.string.fries,
            R.string.smoothies
        )
    }

    override fun getDinnerFoodTypeOptions(): List<Int> {
        return listOf(
            R.string.pizza,
            R.string.pasta,
            R.string.burgers,
            R.string.steak,
            R.string.barbeque,
            R.string.soup,
            R.string.salad,
            R.string.sushi,
            R.string.sandwiches,
            R.string.tacos,
            R.string.burritos,
            R.string.bowls,
            R.string.noodles,
            R.string.ramen,
            R.string.wings,
            R.string.nachos,
            R.string.fries,
            R.string.smoothies
        )
    }

    override fun getDessertFoodTypeOptions(): List<Int> {
        return listOf(
            R.string.ice_cream,
            R.string.cookies,
            R.string.cake,
            R.string.cupcakes,
            R.string.pie,
        )
    }

    override fun getMealOptions(): List<Int> {
        return listOf(
            R.string.breakfast,
            R.string.lunch,
            R.string.dinner,
            R.string.dessert
        )
    }

    override fun getRegionOptions(): List<Int> {
        return listOf(
            R.string.american,
            R.string.chinese,
            R.string.japanese,
            R.string.vietnamese,
            R.string.thai,
            R.string.korean,
            R.string.indian,
            R.string.middle_eastern,
            R.string.taiwanese,
            R.string.mediterranean,
            R.string.greek,
            R.string.french,
            R.string.italian,
            R.string.german,
            R.string.polish,
            R.string.scandinavian,
            R.string.brazilian,
            R.string.mexican,
        )
    }

    override fun getPriceChoices(): List<Price> {
        return listOf(Price.ONE, Price.TWO, Price.THREE, Price.FOUR)
    }
}