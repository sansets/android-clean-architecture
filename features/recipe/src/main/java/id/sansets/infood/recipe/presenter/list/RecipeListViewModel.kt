package id.sansets.infood.recipe.presenter.list

import androidx.lifecycle.*
import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.Recipe
import id.sansets.infood.core.domain.usecase.CoreUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class RecipeListViewModel @Inject constructor(private val useCase: CoreUseCase) : ViewModel() {

    private val _query = MutableLiveData<RecipeListModel>()
    val query: LiveData<RecipeListModel> = _query

    private val _recipes = Transformations.switchMap(_query) {
        useCase.getRecipes(it.query, it.filterFoodCategories).asLiveData()
    }
    val recipes: LiveData<Resource<List<Recipe>>> = Transformations.map(_recipes) { it }

    private val _filterFoodCategoryList = MutableLiveData<ArrayList<FoodCategory>>()
    val filterFoodCategoryList = Transformations.map(_filterFoodCategoryList) { it }

    fun getRecipes(query: String?, filterFoodCategories: List<FoodCategory>?) {
        _query.postValue(
            RecipeListModel(
                query = query,
                filterFoodCategories = filterFoodCategories
            )
        )
    }

    fun updateFoodCategoriesFilter(foodCategories: ArrayList<FoodCategory>) {
        _filterFoodCategoryList.postValue(foodCategories)
    }

    fun removeFoodCategoryFilter(foodCategory: FoodCategory) {
        val foodCategories = _filterFoodCategoryList.value?.apply {
            removeAll { it.id == foodCategory.id }
        }
        _filterFoodCategoryList.postValue(foodCategories)
    }

    fun setFavorite(recipe: Recipe, isFavorite: Boolean) {
        if (!isFavorite) {
            useCase.deleteFavorite(recipe)
        } else {
            useCase.insertFavorite(recipe)
        }
    }
}