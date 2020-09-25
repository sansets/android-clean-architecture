package id.sansets.infood.core.data.source.local.room

import androidx.room.*
import id.sansets.infood.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM ${FavoriteEntity.TABLE_NAME} WHERE ${FavoriteEntity.COLUMN_ID} IN(:id)")
    fun getFavorite(id: Int): Flow<FavoriteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity)
}