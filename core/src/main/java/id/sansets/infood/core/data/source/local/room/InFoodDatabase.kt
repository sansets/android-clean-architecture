package id.sansets.infood.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.sansets.infood.core.data.source.local.entity.FavoriteEntity
import id.sansets.infood.core.data.source.local.entity.FoodCategoryEntity

@Database(entities = [FoodCategoryEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class InFoodDatabase : RoomDatabase() {

    abstract fun foodCategoryDao(): FoodCategoryDao

    abstract fun favoriteDao(): FavoriteDao
}