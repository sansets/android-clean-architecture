package id.sansets.infood.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListFoodCategory(
    val foodCategories: List<FoodCategory> = emptyList()
) : Parcelable