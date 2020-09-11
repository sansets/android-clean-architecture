package id.sansets.infood.core.data.source.local.room

import androidx.room.*
import id.sansets.infood.core.data.source.local.entity.FoodCategoryEntity
import id.sansets.infood.core.data.source.local.entity.FoodCategoryEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodCategoryDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getFoodCategories(): Flow<List<FoodCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodCategories(foodCategories: List<FoodCategoryEntity>)
}