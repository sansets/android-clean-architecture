package id.sansets.infood.recipe.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.sansets.infood.core.di.ViewModelKey
import id.sansets.infood.core.util.ViewModelFactory
import id.sansets.infood.recipe.presenter.filter.RecipeFilterViewModel
import id.sansets.infood.recipe.presenter.list.RecipeListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Suppress("unused")
@Module
abstract class RecipeViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RecipeListViewModel::class)
    abstract fun bindRecipeListViewModel(recipeListViewModel: RecipeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipeFilterViewModel::class)
    abstract fun bindRecipeFilterViewModel(recipeFilterViewModel: RecipeFilterViewModel): ViewModel
}