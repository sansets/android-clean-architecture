package id.sansets.infood.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RecipeEntity.TABLE_NAME)
data class RecipeEntity(

    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Int,

    @ColumnInfo(name = COLUMN_TITLE)
    val title: String?,

    @ColumnInfo(name = COLUMN_SUMMARY)
    val summary: String?,

    @ColumnInfo(name = COLUMN_IMAGE_URL)
    val imageUrl: String?,

    @ColumnInfo(name = COLUMN_IMAGE_TYPE)
    val imageType: String?,

    @ColumnInfo(name = COLUMN_SOURCE_NAME)
    val sourceName: String?,

    @ColumnInfo(name = COLUMN_DISH_TYPES)
    val dishTypes: List<String>?,

    @ColumnInfo(name = COLUMN_ANALYZED_INSTRUCTIONS)
    val analyzedInstructions: List<InstructionEntity>?
) {
    companion object {
        const val TABLE_NAME = "favorite"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_SUMMARY = "summary"
        const val COLUMN_IMAGE_URL = "image_url"
        const val COLUMN_IMAGE_TYPE = "image_type"
        const val COLUMN_SOURCE_NAME = "source_name"
        const val COLUMN_DISH_TYPES = "dish_types"
        const val COLUMN_ANALYZED_INSTRUCTIONS = "analyzed_instructions"
    }
}

data class InstructionEntity(

    @ColumnInfo(name = COLUMN_NAME)
    val name: String?,

    @ColumnInfo(name = COLUMN_STEPS)
    val steps: List<StepEntity>?
) {
    companion object {
        const val COLUMN_NAME = "name"
        const val COLUMN_STEPS = "steps"
    }
}

data class StepEntity(

    @ColumnInfo(name = COLUMN_NUMBER)
    val number: Int?,

    @ColumnInfo(name = COLUMN_STEP)
    val step: String?,

    @ColumnInfo(name = COLUMN_INGREDIENTS)
    val ingredients: List<IngredientEntity>?
) {
    companion object {
        const val COLUMN_NUMBER = "number"
        const val COLUMN_STEP = "step"
        const val COLUMN_INGREDIENTS = "ingredients"
    }
}

data class IngredientEntity(

    @ColumnInfo(name = COLUMN_ID)
    val id: Int?,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String?,

    @ColumnInfo(name = COLUMN_LOCALIZED_NAME)
    val localizedName: String?,

    @ColumnInfo(name = COLUMN_IMAGE)
    val image: String?,
) {
    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_LOCALIZED_NAME = "localized_name"
        const val COLUMN_IMAGE = "image"
    }
}