package id.sansets.infood.core.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import id.sansets.infood.core.R

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}

fun ImageView.loadRecipeImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .fitCenter()
        .placeholder(R.drawable.ic_placeholder_food)
        .error(R.drawable.ic_placeholder_food)
        .into(this)
}