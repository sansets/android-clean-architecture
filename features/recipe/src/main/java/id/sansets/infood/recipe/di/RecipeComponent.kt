package id.sansets.infood.recipe.di

import dagger.Component
import id.sansets.infood.core.di.CoreComponent
import id.sansets.infood.recipe.presenter.list.RecipeListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@RecipeScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [RecipeModule::class, RecipeViewModelModule::class]
)
interface RecipeComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): RecipeComponent
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun inject(fragment: RecipeListFragment)
}