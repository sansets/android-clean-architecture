package id.sansets.infood.core.data.source.local

import androidx.sqlite.db.SimpleSQLiteQuery
import id.sansets.infood.core.data.source.local.entity.RecipeEntity
import id.sansets.infood.core.data.source.local.entity.FoodCategoryEntity
import id.sansets.infood.core.data.source.local.room.RecipeDao
import id.sansets.infood.core.data.source.local.room.FoodCategoryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoreLocalDataSource @Inject constructor(
    private val foodCategoryDao: FoodCategoryDao,
    private val recipeDao: RecipeDao,
) {

    fun getFoodCategories(): Flow<List<FoodCategoryEntity>> = foodCategoryDao.getFoodCategories()

    suspend fun insertFoodCategories(foodCategories: List<FoodCategoryEntity>) =
        foodCategoryDao.insertFoodCategories(foodCategories)

    fun getFavoriteRecipes(title: String? = null): Flow<List<RecipeEntity>> {
        val query = if (title.isNullOrEmpty()) {
            "SELECT * FROM ${RecipeEntity.TABLE_NAME}"
        } else {
            "SELECT * FROM ${RecipeEntity.TABLE_NAME} WHERE ${RecipeEntity.COLUMN_TITLE} LIKE '%$title%'"
        }

        return recipeDao.getRecipes(SimpleSQLiteQuery(query))
    }

    fun getFavorite(id: Int): Flow<RecipeEntity?> = recipeDao.getRecipe(id)

    fun insertFavorite(recipe: RecipeEntity) = recipeDao.insertRecipe(recipe)

    fun deleteFavorite(recipe: RecipeEntity) = recipeDao.deleteRecipe(recipe)
}