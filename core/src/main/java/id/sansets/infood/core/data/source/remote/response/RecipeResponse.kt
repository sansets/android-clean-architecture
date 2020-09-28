package id.sansets.infood.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RecipeResponse(

    @SerializedName(value = "id")
    val id: Int,

    @SerializedName(value = "title")
    val title: String?,

    @SerializedName(value = "summary")
    val summary: String?,

    @SerializedName(value = "image")
    val imageUrl: String?,

    @SerializedName(value = "imageType")
    val imageType: String?,

    @SerializedName(value = "sourceName")
    val sourceName: String?,

    @SerializedName(value = "dishTypes")
    val dishTypes: List<String>?,

    @SerializedName(value = "analyzedInstructions")
    val analyzedInstructions: List<InstructionResponse>?
)