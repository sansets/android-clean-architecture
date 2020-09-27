package id.sansets.infood.core.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientResponse(

    @Json(name = "id")
    val id: Int?,

    @Json(name = "name")
    val name: String?,

    @Json(name = "localizedName")
    val localizedName: String?,

    @Json(name = "image")
    val image: String?,
)