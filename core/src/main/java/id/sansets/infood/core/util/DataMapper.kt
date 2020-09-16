package id.sansets.infood.core.util

import id.sansets.infood.core.data.source.local.entity.FoodCategoryEntity
import id.sansets.infood.core.data.source.remote.response.FoodCategoryResponse
import id.sansets.infood.core.data.source.remote.response.RecipeResponse
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.Recipe

object DataMapper {

    fun mapFoodCategoryResponsesToEntities(input: List<FoodCategoryResponse>): List<FoodCategoryEntity> {
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

    fun mapFoodCategoryEntitiesToDomain(input: List<FoodCategoryEntity>): List<FoodCategory> =
        input.map {
            FoodCategory(
                id = it.id,
                title = it.title,
                iconUrl = it.iconUrl
            )
        }

    fun mapRecipeResponseToDomain(input: RecipeResponse): Recipe = Recipe(
        id = input.id,
        title = input.title ?: "",
        imageUrl = input.imageUrl ?: "",
        sourceName = input.sourceName ?: "",
        isFavorite = false,
    )
}