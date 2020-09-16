package id.sansets.infood.recipe.presenter.list

import androidx.lifecycle.*
import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.Recipe
import id.sansets.infood.core.domain.usecase.CoreUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class RecipeListViewModel @Inject constructor(private val coreUseCase: CoreUseCase) : ViewModel() {

    private val _query = MutableLiveData<RecipeListModel>()
    val query: LiveData<RecipeListModel> = _query

    private val _recipes = Transformations.switchMap(_query) {
        coreUseCase.getRecipes(it.query, it.filterFoodCategories).asLiveData()
    }
    val recipes: LiveData<Resource<List<Recipe>>> = Transformations.map(_recipes) { it }

    fun getRecipes(query: String?, filterFoodCategories: String?) {
        _query.postValue(
            RecipeListModel(
                query = query,
                filterFoodCategories = filterFoodCategories
            )
        )
    }
}