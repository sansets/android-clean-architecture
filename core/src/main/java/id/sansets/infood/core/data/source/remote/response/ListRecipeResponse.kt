package id.sansets.infood.core.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListRecipeResponse(

    @Json(name = "results")
    val results: List<RecipeResponse>
)