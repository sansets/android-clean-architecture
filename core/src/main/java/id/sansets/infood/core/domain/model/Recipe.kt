package id.sansets.infood.core.domain.model

data class Recipe(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val sourceName: String,
    val isFavorite: Boolean
)