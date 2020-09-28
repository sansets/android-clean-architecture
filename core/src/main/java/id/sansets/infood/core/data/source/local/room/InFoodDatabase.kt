package id.sansets.infood.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.sansets.infood.core.data.source.local.entity.RecipeEntity
import id.sansets.infood.core.data.source.local.entity.FoodCategoryEntity

@Database(entities = [FoodCategoryEntity::class, RecipeEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class InFoodDatabase : RoomDatabase() {

    abstract fun foodCategoryDao(): FoodCategoryDao

    abstract fun favoriteDao(): RecipeDao
}