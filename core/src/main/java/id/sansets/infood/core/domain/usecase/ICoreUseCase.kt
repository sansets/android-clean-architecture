package id.sansets.infood.core.domain.usecase

import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.FoodCategory
import kotlinx.coroutines.flow.Flow

interface ICoreUseCase {

    fun getFoodCategories(): Flow<Resource<List<FoodCategory>>>
}