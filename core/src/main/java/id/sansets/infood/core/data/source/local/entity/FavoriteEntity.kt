package id.sansets.infood.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.sansets.infood.core.data.source.local.entity.FavoriteEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FavoriteEntity(

    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Int,
) {
    companion object {
        const val TABLE_NAME = "favorite"
        const val COLUMN_ID = "id"
    }
}