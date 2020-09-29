package id.sansets.infood.core.data.source.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.sansets.infood.core.data.source.local.entity.IngredientEntity
import id.sansets.infood.core.data.source.local.entity.InstructionEntity
import id.sansets.infood.core.data.source.local.entity.StepEntity

class Converters {

    @TypeConverter
    fun fromIngredientObject(ingredient: IngredientEntity?): String {
        val type = object : TypeToken<IngredientEntity?>() {}.type
        return Gson().toJson(ingredient, type)
    }

    @TypeConverter
    fun toIngredientObject(ingredient: String?): IngredientEntity? {
        val type = object : TypeToken<IngredientEntity?>() {}.type
        return Gson().fromJson(ingredient, type)
    }

    @TypeConverter
    fun fromStepObject(step: StepEntity?): String {
        val type = object : TypeToken<StepEntity?>() {}.type
        return Gson().toJson(step, type)
    }

    @TypeConverter
    fun toStepObject(step: String?): StepEntity? {
        val type = object : TypeToken<StepEntity?>() {}.type
        return Gson().fromJson(step, type)
    }

    @TypeConverter
    fun fromInstructionObject(instruction: InstructionEntity?): String {
        val type = object : TypeToken<InstructionEntity?>() {}.type
        return Gson().toJson(instruction, type)
    }

    @TypeConverter
    fun toInstructionObject(instruction: String?): InstructionEntity? {
        val type = object : TypeToken<InstructionEntity?>() {}.type
        return Gson().fromJson(instruction, type)
    }

    @TypeConverter
    fun fromListInstructionObject(instructions: List<InstructionEntity>?): String {
        val type = object : TypeToken<List<InstructionEntity>?>() {}.type
        return Gson().toJson(instructions, type)
    }

    @TypeConverter
    fun toListInstructionObject(instruction: String?): List<InstructionEntity>? {
        val type = object : TypeToken<List<InstructionEntity>?>() {}.type
        return Gson().fromJson(instruction, type)
    }

    @TypeConverter
    fun fromListStringObject(list: List<String>?): String? {
        val type = object : TypeToken<List<String>?>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toListStringObject(list: String?): List<String>? {
        val type = object : TypeToken<List<String>?>() {}.type
        return Gson().fromJson(list, type)
    }
}