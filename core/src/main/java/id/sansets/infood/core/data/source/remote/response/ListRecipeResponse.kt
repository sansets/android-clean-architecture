package id.sansets.infood.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListRecipeResponse(

    @SerializedName(value = "results")
    val results: List<RecipeResponse>
)