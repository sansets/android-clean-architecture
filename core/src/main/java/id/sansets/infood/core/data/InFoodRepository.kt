package id.sansets.infood.core.data

import id.sansets.infood.core.data.source.local.LocalDataSource
import id.sansets.infood.core.data.source.remote.RemoteDataSource
import id.sansets.infood.core.data.source.remote.network.ApiResponse
import id.sansets.infood.core.data.source.remote.response.FoodCategoryResponse
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.Recipe
import id.sansets.infood.core.domain.repository.ICoreRepository
import id.sansets.infood.core.util.AppExecutors
import id.sansets.infood.core.util.DataMapper
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InFoodRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : ICoreRepository {

    override fun getFoodCategories(): Flow<Resource<List<FoodCategory>>> =
        object : NetworkBoundResource<List<FoodCategory>, List<FoodCategoryResponse>>() {
            override fun loadFromDB(): Flow<List<FoodCategory>> =
                localDataSource.getFoodCategories().map {
                    DataMapper.mapFoodCategoryEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<FoodCategory>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<FoodCategoryResponse>>> =
                remoteDataSource.getFoodCategories()

            override suspend fun saveCallResult(data: List<FoodCategoryResponse>) {
                val foodCategories = DataMapper.mapFoodCategoryResponsesToEntities(data)
                localDataSource.insertFoodCategories(foodCategories)
            }
        }.asFlow()

    override fun getRecipes(
        query: String?,
        foodCategories: List<FoodCategory>?,
        addRecipeInformation: Boolean?
    ): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading())
            emitAll(remoteDataSource.getRecipes(
                query = query,
                type = foodCategories?.map { it.title }?.joinToString()
            ).map {
                when (it) {
                    is ApiResponse.Success -> {
                        Resource.Success(it.data.map { recipeResponse ->
                            val isFavorite = localDataSource
                                .getFavorite(recipeResponse.id).first() != null
                            DataMapper.mapRecipeResponseToDomain(recipeResponse, isFavorite)
                        })
                    }
                    is ApiResponse.Empty -> {
                        Resource.Success(emptyList())
                    }
                    is ApiResponse.Error -> {
                        Resource.Error(it.errorMessage)
                    }
                }
            })
        }
    }

    override fun getFavoriteRecipes(
        query: String?,
        foodCategories: List<FoodCategory>?,
    ): Flow<Resource<List<Recipe>>> {
        return flow {
            emitAll(localDataSource.getFavoriteRecipes(query).map {
                val foodCategoriesString = foodCategories?.map { foodCategory ->
                    foodCategory.title?.toLowerCase(Locale.getDefault())
                }

                val results = if (!foodCategoriesString.isNullOrEmpty()) {
                    it.filter { recipeEntity ->
                        recipeEntity.dishTypes?.map { dishType ->
                            dishType.toLowerCase(Locale.getDefault())
                        }?.any(foodCategoriesString::contains) == true
                    }
                } else {
                    it
                }

                Resource.Success(results.map { recipeEntity ->
                    DataMapper.mapRecipeEntityToDomain(recipeEntity)
                })
            })
        }
    }

    override fun isFavorite(recipe: Recipe): Flow<Resource<Boolean>> {
        return flow {
            emitAll(localDataSource.getFavorite(recipe.id).map {
                Resource.Success(it != null)
            })
        }
    }

    override fun insertFavorite(recipe: Recipe) {
        appExecutors.diskIO().execute {
            localDataSource.insertFavorite(DataMapper.mapRecipeDomainToEntity(recipe))
        }
    }

    override fun deleteFavorite(recipe: Recipe) {
        appExecutors.diskIO().execute {
            localDataSource.deleteFavorite(DataMapper.mapRecipeDomainToEntity(recipe))
        }
    }
}