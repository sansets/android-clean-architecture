package id.sansets.infood.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class IngredientResponse(

    @SerializedName(value = "id")
    val id: Int?,

    @SerializedName(value = "name")
    val name: String?,

    @SerializedName(value = "localizedName")
    val localizedName: String?,

    @SerializedName(value = "image")
    val image: String?,
)