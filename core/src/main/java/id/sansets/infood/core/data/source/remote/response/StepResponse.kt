package id.sansets.infood.core.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StepResponse(

    @Json(name = "number")
    val number: Int?,

    @Json(name = "step")
    val step: String?,

    @Json(name = "ingredients")
    val ingredients: List<IngredientResponse>?
)