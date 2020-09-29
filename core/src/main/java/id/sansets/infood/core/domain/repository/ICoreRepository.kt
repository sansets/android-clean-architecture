package id.sansets.infood.core.domain.repository

import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface ICoreRepository {

    fun getFoodCategories(): Flow<Resource<List<FoodCategory>>>

    fun getRecipes(
        query: String?,
        foodCategories: List<FoodCategory>?,
        addRecipeInformation: Boolean? = true
    ): Flow<Resource<List<Recipe>>>

    fun getFavoriteRecipes(
        query: String?,
        foodCategories: List<FoodCategory>?,
    ): Flow<Resource<List<Recipe>>>

    fun isFavorite(recipe: Recipe): Flow<Resource<Boolean>>

    fun insertFavorite(recipe: Recipe)

    fun deleteFavorite(recipe: Recipe)
}