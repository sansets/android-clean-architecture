package id.sansets.infood.core.data.source.remote

import id.sansets.infood.core.BuildConfig
import id.sansets.infood.core.data.source.remote.network.ApiResponse
import id.sansets.infood.core.data.source.remote.network.CoreApiService
import id.sansets.infood.core.data.source.remote.response.FoodCategoryResponse
import id.sansets.infood.core.data.source.remote.response.RecipeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: CoreApiService) {

    suspend fun getFoodCategories(): Flow<ApiResponse<List<FoodCategoryResponse>>> {
        return flow {
            try {
                val dataArray = listOf(
                    FoodCategoryResponse(
                        1,
                        "Main Course",
                        "https://firebasestorage.googleapis.com/v0/b/infood-e9a12.appspot.com/o/food_categories%2Fic_color_main_course_64.png?alt=media&token=db4bc3ac-9d91-4bc4-be3d-006fe6d90bbf"
                    ),
                    FoodCategoryResponse(
                        2,
                        "Dessert",
                        "https://firebasestorage.googleapis.com/v0/b/infood-e9a12.appspot.com/o/food_categories%2Fic_color_dessert_64.png?alt=media&token=f88b1d93-c80f-40e4-af93-b84665a887ec"
                    ),
                    FoodCategoryResponse(
                        3,
                        "Drink",
                        "https://firebasestorage.googleapis.com/v0/b/infood-e9a12.appspot.com/o/food_categories%2Fic_color_drink_64.png?alt=media&token=fa6901f5-2f0d-4bed-823c-c047ba65f6ac"
                    ),
                    FoodCategoryResponse(
                        4,
                        "Bread",
                        "https://firebasestorage.googleapis.com/v0/b/infood-e9a12.appspot.com/o/food_categories%2Fic_color_bread_64.png?alt=media&token=7aa5e33a-7ccb-4ae3-865b-55c63817da8c"
                    ),
                    FoodCategoryResponse(
                        5,
                        "Soup",
                        "https://firebasestorage.googleapis.com/v0/b/infood-e9a12.appspot.com/o/food_categories%2Fic_color_soup_64.png?alt=media&token=e9c35aae-92b7-4766-a7f9-491f854bab1e"
                    ),
                    FoodCategoryResponse(
                        6,
                        "Snack",
                        "https://firebasestorage.googleapis.com/v0/b/infood-e9a12.appspot.com/o/food_categories%2Fic_color_snack_64.png?alt=media&token=3e34fb29-17cd-49db-a982-9a0bb01ef2aa"
                    ),
                    FoodCategoryResponse(
                        7,
                        "Breakfast",
                        "https://firebasestorage.googleapis.com/v0/b/infood-e9a12.appspot.com/o/food_categories%2Fic_color_breakfast_64.png?alt=media&token=7c78869a-ce98-4ec9-9c0a-21eb87cbdaf7"
                    ),
                )

                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRecipes(
        query: String?,
        type: String?,
        addRecipeInformation: Boolean? = true
    ): Flow<ApiResponse<List<RecipeResponse>>> {
        return flow {
            try {
                val response = apiService.getRecipes(
                    apiKey = BuildConfig.SPOONACULAR_API_KEY,
                    query = query,
                    type = type,
                    addRecipeInformation = addRecipeInformation
                )
                val dataArray = response.results

                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}