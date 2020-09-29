package id.sansets.infood.core.di

import dagger.Binds
import dagger.Module
import id.sansets.infood.core.data.InFoodRepository
import id.sansets.infood.core.domain.repository.ICoreRepository

@Module(includes = [CoreNetworkModule::class, CoreDatabaseModule::class])
abstract class CoreRepositoryModule {

    @Binds
    abstract fun provideRepository(repository: InFoodRepository): ICoreRepository
}