package id.sansets.infood.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.sansets.infood.core.data.source.local.entity.FoodCategoryEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FoodCategoryEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = COLUMN_ID)
    var id: Int,

    @ColumnInfo(name = COLUMN_TITLE)
    var title: String?,

    @ColumnInfo(name = COLUMN_ICON_URL)
    var iconUrl: String?,
) {

    companion object {
        const val TABLE_NAME = "food_category"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_ICON_URL = "icon_url"
    }
}