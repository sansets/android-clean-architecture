package id.sansets.infood.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import id.sansets.infood.core.data.source.local.room.FavoriteDao
import id.sansets.infood.core.data.source.local.room.FoodCategoryDao
import id.sansets.infood.core.data.source.local.room.InFoodDatabase
import javax.inject.Singleton

@Module
class CoreDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): InFoodDatabase =
        Room.databaseBuilder(
            context,
            InFoodDatabase::class.java, "InFood.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideFoodCategoryDao(database: InFoodDatabase): FoodCategoryDao = database.foodCategoryDao()

    @Provides
    fun provideFavoriteDao(database: InFoodDatabase): FavoriteDao = database.favoriteDao()
}