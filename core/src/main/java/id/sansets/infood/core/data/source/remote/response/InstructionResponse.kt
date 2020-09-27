package id.sansets.infood.core.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InstructionResponse(

    @Json(name = "name")
    val name: String?,

    @Json(name = "steps")
    val steps: List<StepResponse>?
)