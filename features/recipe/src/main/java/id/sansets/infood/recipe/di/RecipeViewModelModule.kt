package id.sansets.infood.recipe.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.sansets.infood.core.di.ViewModelKey
import id.sansets.infood.core.util.ViewModelFactory
import id.sansets.infood.recipe.presenter.list.RecipeListViewModel

@Suppress("unused")
@Module
abstract class RecipeViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RecipeListViewModel::class)
    abstract fun bindRecipeListViewModel(homeViewModel: RecipeListViewModel): ViewModel
}