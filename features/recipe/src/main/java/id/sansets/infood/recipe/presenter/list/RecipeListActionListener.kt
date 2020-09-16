package id.sansets.infood.recipe.presenter.list

import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.Recipe

interface RecipeListActionListener {

    fun onRefreshRecipes()

    fun onOpenRecipeFilter()

    fun onRemoveFilterFoodCategory(foodCategory: FoodCategory)

    fun onOpenRecipeDetail(recipe: Recipe?)

    fun onUpdateFavoriteRecipe(favorite: Boolean, recipe: Recipe?)
}