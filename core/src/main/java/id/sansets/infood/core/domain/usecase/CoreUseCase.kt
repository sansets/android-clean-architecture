package id.sansets.infood.core.domain.usecase

import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.Recipe
import id.sansets.infood.core.domain.repository.ICoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoreUseCase @Inject constructor(private val repository: ICoreRepository) : ICoreUseCase {

    override fun getFoodCategories() = repository.getFoodCategories()

    override fun getRecipes(
        query: String?,
        foodCategories: List<FoodCategory>?,
        addRecipeInformation: Boolean?
    ): Flow<Resource<List<Recipe>>> =
        repository.getRecipes(query, foodCategories, addRecipeInformation)

    override fun getFavoriteRecipes(
        query: String?,
        foodCategories: List<FoodCategory>?,
    ): Flow<Resource<List<Recipe>>> = repository.getFavoriteRecipes(query, foodCategories)

    override fun isFavorite(recipe: Recipe): Flow<Resource<Boolean>> = repository.isFavorite(recipe)

    override fun insertFavorite(recipe: Recipe) = repository.insertFavorite(recipe)

    override fun deleteFavorite(recipe: Recipe) = repository.deleteFavorite(recipe)
}