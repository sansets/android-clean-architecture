package id.sansets.infood.recipe.presenter.favorite

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
class FavoriteViewModel @Inject constructor(private val useCase: CoreUseCase) : ViewModel() {

    private val _query = MutableLiveData<FavoriteModel>()
    val query: LiveData<FavoriteModel> = _query

    private val _recipes = Transformations.switchMap(_query) {
        useCase.getFavoriteRecipes(it.query, it.filterFoodCategories).asLiveData()
    }
    val recipes: LiveData<Resource<List<Recipe>>> = Transformations.map(_recipes) { it }

    private val _filterFoodCategoryList = MutableLiveData<ArrayList<FoodCategory>>()
    val filterFoodCategoryList = Transformations.map(_filterFoodCategoryList) { it }

    private val _emptyFavoriteList = MutableLiveData<Boolean>()
    val emptyFavoriteList = Transformations.map(_emptyFavoriteList) { it }

    private val _emptySearchResult = MutableLiveData<Boolean>()
    val emptySearchResult = Transformations.map(_emptySearchResult) { it }

    fun getRecipes(query: String? = "", filterFoodCategories: List<FoodCategory>? = emptyList()) {
        _query.postValue(
            FavoriteModel(
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

    fun checkFavoriteListEmpty(recipes: List<Recipe>?) {
        if (!_query.value?.query.isNullOrEmpty()
            || !_query.value?.filterFoodCategories.isNullOrEmpty()
        ) {
            _emptyFavoriteList.postValue(false)
            _emptySearchResult.postValue(recipes.isNullOrEmpty())
        } else {
            _emptyFavoriteList.postValue(recipes.isNullOrEmpty())
            _emptySearchResult.postValue(false)
        }
    }
}