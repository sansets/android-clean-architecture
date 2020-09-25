package id.sansets.infood.core.domain.usecase

import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.Recipe
import id.sansets.infood.core.domain.repository.ICoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoreUseCase @Inject constructor(private val repository: ICoreRepository) : ICoreUseCase {

    override fun getFoodCategories() = repository.getFoodCategories()

    override fun getRecipes(
        query: String?,
        type: String?,
        addRecipeInformation: Boolean?
    ): Flow<Resource<List<Recipe>>> = repository.getRecipes(query, type, addRecipeInformation)

    override fun insertFavorite(id: Int) = repository.insertFavorite(id)

    override fun deleteFavorite(id: Int) = repository.deleteFavorite(id)
}