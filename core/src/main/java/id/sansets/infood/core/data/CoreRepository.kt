package id.sansets.infood.core.data

import id.sansets.infood.core.data.source.local.CoreLocalDataSource
import id.sansets.infood.core.data.source.remote.CoreRemoteDataSource
import id.sansets.infood.core.data.source.remote.network.ApiResponse
import id.sansets.infood.core.data.source.remote.response.FoodCategoryResponse
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.repository.ICoreRepository
import id.sansets.infood.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoreRepository @Inject constructor(
    private val remoteDataSource: CoreRemoteDataSource,
    private val localDataSource: CoreLocalDataSource,
) : ICoreRepository {

    override fun getFoodCategories(): Flow<Resource<List<FoodCategory>>> =
        object : NetworkBoundResource<List<FoodCategory>, List<FoodCategoryResponse>>() {
            override fun loadFromDB(): Flow<List<FoodCategory>> {
                return localDataSource.getFoodCategories().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<FoodCategory>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<FoodCategoryResponse>>> =
                remoteDataSource.getFoodCategories()

            override suspend fun saveCallResult(data: List<FoodCategoryResponse>) {
                val foodCategories = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertFoodCategories(foodCategories)
            }
        }.asFlow()
}