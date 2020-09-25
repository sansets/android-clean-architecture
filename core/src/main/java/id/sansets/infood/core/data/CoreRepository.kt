package id.sansets.infood.core.data

import id.sansets.infood.core.data.source.local.CoreLocalDataSource
import id.sansets.infood.core.data.source.local.entity.FavoriteEntity
import id.sansets.infood.core.data.source.remote.CoreRemoteDataSource
import id.sansets.infood.core.data.source.remote.network.ApiResponse
import id.sansets.infood.core.data.source.remote.response.FoodCategoryResponse
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.Recipe
import id.sansets.infood.core.domain.repository.ICoreRepository
import id.sansets.infood.core.util.AppExecutors
import id.sansets.infood.core.util.DataMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoreRepository @Inject constructor(
    private val remoteDataSource: CoreRemoteDataSource,
    private val localDataSource: CoreLocalDataSource,
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
        type: String?,
        addRecipeInformation: Boolean?
    ): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading())
            emitAll(remoteDataSource.getRecipes(query = query, type = type).map {
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

    override fun insertFavorite(id: Int) {
        val favoriteEntity = FavoriteEntity(id = id)
        appExecutors.diskIO().execute { localDataSource.insertFavorite(favoriteEntity) }
    }

    override fun deleteFavorite(id: Int) {
        val favoriteEntity = FavoriteEntity(id = id)
        appExecutors.diskIO().execute { localDataSource.deleteFavorite(favoriteEntity) }
    }
}