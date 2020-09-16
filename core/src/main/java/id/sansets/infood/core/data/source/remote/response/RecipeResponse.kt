package id.sansets.infood.core.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeResponse(

    @Json(name = "id")
    val id: Int,

    @Json(name = "title")
    val title: String?,

    @Json(name = "image")
    val imageUrl: String?,

    @Json(name = "sourceName")
    val sourceName: String?
)