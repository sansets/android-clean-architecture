package id.sansets.infood.core.util

import id.sansets.infood.core.data.source.local.entity.*
import id.sansets.infood.core.data.source.remote.response.FoodCategoryResponse
import id.sansets.infood.core.data.source.remote.response.RecipeResponse
import id.sansets.infood.core.domain.model.*

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

    fun mapRecipeResponseToDomain(
        input: RecipeResponse,
        isFavorite: Boolean = false,
    ): Recipe {
        val steps = ArrayList<Step>()
        val ingredients = ArrayList<Ingredient>()

        input.analyzedInstructions?.forEach { instructionResponse ->
            instructionResponse.steps?.forEach { stepResponse ->
                steps.add(
                    Step(
                        number = stepResponse.number ?: 0,
                        step = stepResponse.step ?: "",
                        ingredients = stepResponse.ingredients?.map { ingredientResponse ->
                            Ingredient(
                                id = ingredientResponse.id ?: 0,
                                name = ingredientResponse.name ?: "",
                                localizedName = ingredientResponse.localizedName ?: "",
                                image = ingredientResponse.image ?: ""
                            )
                        } ?: emptyList()
                    )
                )
            }
        }

        input.analyzedInstructions?.forEach { instructionResponse ->
            instructionResponse.steps?.forEach { stepResponse ->
                stepResponse.ingredients?.forEach { ingredientResponse ->
                    if (!ingredients.any { it.id == ingredientResponse.id }) {
                        ingredients.add(
                            Ingredient(
                                id = ingredientResponse.id ?: 0,
                                name = ingredientResponse.name ?: "",
                                localizedName = ingredientResponse.localizedName ?: "",
                                image = ingredientResponse.image ?: ""
                            )
                        )
                    }
                }
            }
        }

        return Recipe(
            id = input.id,
            title = input.title ?: "",
            summary = input.summary ?: "",
            imageUrl = input.imageUrl ?: "",
            imageType = input.imageType ?: "",
            sourceName = input.sourceName ?: "",
            dishTypes = input.dishTypes ?: emptyList(),
            analyzedInstructions = input.analyzedInstructions?.map { instructionResponse ->
                Instruction(
                    name = instructionResponse.name ?: "",
                    steps = instructionResponse.steps?.map { stepResponse ->
                        Step(
                            number = stepResponse.number ?: 0,
                            step = stepResponse.step ?: "",
                            ingredients = stepResponse.ingredients?.map { ingredientResponse ->
                                Ingredient(
                                    id = ingredientResponse.id ?: 0,
                                    name = ingredientResponse.name ?: "",
                                    localizedName = ingredientResponse.localizedName ?: "",
                                    image = ingredientResponse.image ?: ""
                                )
                            } ?: emptyList()
                        )
                    } ?: emptyList()
                )
            } ?: emptyList(),
            steps = steps,
            ingredients = ingredients,
            isFavorite = isFavorite,
        )
    }

    fun mapRecipeEntityToDomain(input: RecipeEntity): Recipe {
        val steps = ArrayList<Step>()
        val ingredients = ArrayList<Ingredient>()

        input.analyzedInstructions?.forEach { instructionEntity ->
            instructionEntity.steps?.forEach { stepEntity ->
                steps.add(
                    Step(
                        number = stepEntity.number ?: 0,
                        step = stepEntity.step ?: "",
                        ingredients = stepEntity.ingredients?.map { ingredientResponse ->
                            Ingredient(
                                id = ingredientResponse.id ?: 0,
                                name = ingredientResponse.name ?: "",
                                localizedName = ingredientResponse.localizedName ?: "",
                                image = ingredientResponse.image ?: ""
                            )
                        } ?: emptyList()
                    )
                )
            }
        }

        input.analyzedInstructions?.forEach { instruction ->
            instruction.steps?.forEach { step ->
                step.ingredients?.forEach { ingredientEntity ->
                    if (!ingredients.any { it.id == ingredientEntity.id }) {
                        ingredients.add(
                            Ingredient(
                                id = ingredientEntity.id ?: 0,
                                name = ingredientEntity.name ?: "",
                                localizedName = ingredientEntity.localizedName ?: "",
                                image = ingredientEntity.image ?: ""
                            )
                        )
                    }
                }
            }
        }

        return Recipe(
            id = input.id,
            title = input.title ?: "",
            summary = input.summary ?: "",
            imageUrl = input.imageUrl ?: "",
            imageType = input.imageType ?: "",
            sourceName = input.sourceName ?: "",
            dishTypes = input.dishTypes ?: emptyList(),
            analyzedInstructions = input.analyzedInstructions?.map { instructionResponse ->
                Instruction(
                    name = instructionResponse.name ?: "",
                    steps = instructionResponse.steps?.map { stepResponse ->
                        Step(
                            number = stepResponse.number ?: 0,
                            step = stepResponse.step ?: "",
                            ingredients = stepResponse.ingredients?.map { ingredientResponse ->
                                Ingredient(
                                    id = ingredientResponse.id ?: 0,
                                    name = ingredientResponse.name ?: "",
                                    localizedName = ingredientResponse.localizedName ?: "",
                                    image = ingredientResponse.image ?: ""
                                )
                            } ?: emptyList()
                        )
                    } ?: emptyList()
                )
            } ?: emptyList(),
            steps = steps,
            ingredients = ingredients,
            isFavorite = true,
        )
    }

    fun mapRecipeDomainToEntity(input: Recipe): RecipeEntity {
        val steps = ArrayList<StepEntity>()
        val ingredients = ArrayList<IngredientEntity>()

        input.analyzedInstructions.forEach { instruction ->
            instruction.steps.forEach { step ->
                steps.add(
                    StepEntity(
                        number = step.number,
                        step = step.step,
                        ingredients = step.ingredients.map { ingredient ->
                            IngredientEntity(
                                id = ingredient.id,
                                name = ingredient.name,
                                localizedName = ingredient.localizedName,
                                image = ingredient.image
                            )
                        }
                    )
                )
            }
        }

        input.analyzedInstructions.forEach { instruction ->
            instruction.steps.forEach { step ->
                step.ingredients.forEach { ingredient ->
                    if (!ingredients.any { it.id == ingredient.id }) {
                        ingredients.add(
                            IngredientEntity(
                                id = ingredient.id,
                                name = ingredient.name,
                                localizedName = ingredient.localizedName,
                                image = ingredient.image
                            )
                        )
                    }
                }
            }
        }

        return RecipeEntity(
            id = input.id,
            title = input.title,
            summary = input.summary,
            imageUrl = input.imageUrl,
            imageType = input.imageType,
            sourceName = input.sourceName,
            dishTypes = input.dishTypes,
            analyzedInstructions = input.analyzedInstructions.map { instruction ->
                InstructionEntity(
                    name = instruction.name,
                    steps = instruction.steps.map { response ->
                        StepEntity(
                            number = response.number,
                            step = response.step,
                            ingredients = response.ingredients.map { ingredientResponse ->
                                IngredientEntity(
                                    id = ingredientResponse.id,
                                    name = ingredientResponse.name,
                                    localizedName = ingredientResponse.localizedName,
                                    image = ingredientResponse.image
                                )
                            }
                        )
                    }
                )
            },
        )
    }
}