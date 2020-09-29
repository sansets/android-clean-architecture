package id.sansets.infood.core.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import id.sansets.infood.core.data.source.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @RawQuery(observedEntities = [RecipeEntity::class])
    fun getRecipes(query: SupportSQLiteQuery): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM ${RecipeEntity.TABLE_NAME} WHERE ${RecipeEntity.COLUMN_ID} IN(:id)")
    fun getRecipe(id: Int): Flow<RecipeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeEntity)

    @Delete
    fun deleteRecipe(recipe: RecipeEntity)
}