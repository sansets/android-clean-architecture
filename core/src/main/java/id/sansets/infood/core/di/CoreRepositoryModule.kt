package id.sansets.infood.core.di

import dagger.Binds
import dagger.Module
import id.sansets.infood.core.data.CoreRepository
import id.sansets.infood.core.domain.repository.ICoreRepository

@Module(includes = [CoreNetworkModule::class, CoreDatabaseModule::class])
abstract class CoreRepositoryModule {

    @Binds
    abstract fun provideRepository(repository: CoreRepository): ICoreRepository
}