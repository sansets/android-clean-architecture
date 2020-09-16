package id.sansets.infood.core.data.source.remote.network

import id.sansets.infood.core.data.source.remote.response.ListRecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CoreApiService {

    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String?,
        @Query("type") type: String?,
        @Query("addRecipeInformation") addRecipeInformation: Boolean? = true
    ): ListRecipeResponse
}