package id.sansets.infood.home.presenter

import id.sansets.infood.core.domain.model.FoodCategory

interface HomeActionListener {

    fun onFoodCategoryClicked(foodCategory: FoodCategory?)
}