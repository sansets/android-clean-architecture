package id.sansets.infood.core.domain.repository

import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.FoodCategory
import kotlinx.coroutines.flow.Flow

interface ICoreRepository {

    fun getFoodCategories(): Flow<Resource<List<FoodCategory>>>
}