package id.sansets.infood.recipe.presenter.favorite

import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.Recipe

interface FavoriteActionListener {

    fun onRefreshRecipes()

    fun onOpenRecipeFilter()

    fun onRemoveFilterFoodCategory(foodCategory: FoodCategory)

    fun onOpenRecipeDetail(recipe: Recipe)

    fun onUpdateFavoriteRecipe(favorite: Boolean, recipe: Recipe)
}