package id.sansets.infood.recipe.presenter.list

import id.sansets.infood.core.domain.model.FoodCategory

data class RecipeListModel(
    val query: String?,
    val filterFoodCategories: List<FoodCategory>?
)