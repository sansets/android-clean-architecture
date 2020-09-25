package id.sansets.infood.core.domain.repository

import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface ICoreRepository {

    fun getFoodCategories(): Flow<Resource<List<FoodCategory>>>

    fun getRecipes(
        query: String?,
        type: String?,
        addRecipeInformation: Boolean? = true
    ): Flow<Resource<List<Recipe>>>

    fun insertFavorite(id: Int)

    fun deleteFavorite(id: Int)
}