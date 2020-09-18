package id.sansets.infood.recipe.presenter.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.sansets.infood.core.domain.usecase.CoreUseCase
import javax.inject.Inject

class RecipeFilterViewModel @Inject constructor(coreUseCase: CoreUseCase) : ViewModel() {

    val foodCategories = coreUseCase.getFoodCategories().asLiveData()
}