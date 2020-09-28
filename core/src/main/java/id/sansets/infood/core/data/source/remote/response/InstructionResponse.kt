package id.sansets.infood.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class InstructionResponse(

    @SerializedName(value = "name")
    val name: String?,

    @SerializedName(value = "steps")
    val steps: List<StepResponse>?
)