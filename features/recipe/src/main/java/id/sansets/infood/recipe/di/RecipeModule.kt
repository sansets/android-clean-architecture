package id.sansets.infood.recipe.di

import dagger.Binds
import dagger.Module
import id.sansets.infood.core.domain.usecase.CoreUseCase
import id.sansets.infood.core.domain.usecase.ICoreUseCase

@Module
abstract class RecipeModule {

    @Binds
    abstract fun provideCoreUseCase(useCase: CoreUseCase): ICoreUseCase
}