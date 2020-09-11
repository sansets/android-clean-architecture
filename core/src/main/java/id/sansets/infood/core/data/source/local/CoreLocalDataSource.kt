package id.sansets.infood.core.data.source.local

import id.sansets.infood.core.data.source.local.entity.FoodCategoryEntity
import id.sansets.infood.core.data.source.local.room.FoodCategoryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoreLocalDataSource @Inject constructor(private val foodCategoryDao: FoodCategoryDao) {

    fun getFoodCategories(): Flow<List<FoodCategoryEntity>> = foodCategoryDao.getFoodCategories()

    suspend fun insertFoodCategories(foodCategories: List<FoodCategoryEntity>) = foodCategoryDao.insertFoodCategories(foodCategories)
}