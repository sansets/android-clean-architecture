package id.sansets.infood.recipe.presenter.favorite

import id.sansets.infood.core.domain.model.FoodCategory

data class FavoriteModel(
    val query: String?,
    val filterFoodCategories: List<FoodCategory>?
)