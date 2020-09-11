package id.sansets.infood.core.util

import id.sansets.infood.core.data.source.local.entity.FoodCategoryEntity
import id.sansets.infood.core.data.source.remote.response.FoodCategoryResponse
import id.sansets.infood.core.domain.model.FoodCategory

object DataMapper {

    fun mapResponsesToEntities(input: List<FoodCategoryResponse>): List<FoodCategoryEntity> {
        val foodCategories = ArrayList<FoodCategoryEntity>()
        input.map {
            val foodCategory = FoodCategoryEntity(
                id = it.id,
                title = it.title,
                iconUrl = it.iconUrl
            )
            foodCategories.add(foodCategory)
        }
        return foodCategories
    }

    fun mapEntitiesToDomain(input: List<FoodCategoryEntity>): List<FoodCategory> =
        input.map {
            FoodCategory(
                id = it.id,
                title = it.title,
                iconUrl = it.iconUrl
            )
        }

    fun mapDomainToEntity(input: FoodCategory) = FoodCategoryEntity(
        id = input.id,
        title = input.title,
        iconUrl = input.iconUrl
    )
}