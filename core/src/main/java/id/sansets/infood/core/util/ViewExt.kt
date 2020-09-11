package id.sansets.infood.core.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout

fun View.visible() {
    val shortAnimationDuration =
        context?.resources?.getInteger(android.R.integer.config_shortAnimTime) ?: 0

    // Set the content view to 0% opacity but visible, so that it is visible
    // (but fully transparent) during the animation.
    alpha = 0f
    visibility = View.VISIBLE

    // Animate the content view to 100% opacity, and clear any animation
    // listener set on the view.
    animate()
        .alpha(1f)
        .setDuration(shortAnimationDuration.toLong())
        .setListener(null)
}

fun View.gone() {
    val shortAnimationDuration =
        context?.resources?.getInteger(android.R.integer.config_shortAnimTime) ?: 0

    // Animate the showProgress view to 0% opacity. After the animation ends,
    // set its visibility to GONE as an optimization step (it won't
    // participate in layout passes, etc.)
    animate()
        ?.alpha(0f)
        ?.setDuration(shortAnimationDuration.toLong())
        ?.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.GONE
            }
        })
}

fun View.invisible() {
    val shortAnimationDuration =
        context?.resources?.getInteger(android.R.integer.config_shortAnimTime) ?: 0

    // Animate the showProgress view to 0% opacity. After the animation ends,
    // set its visibility to GONE as an optimization step (it won't
    // participate in layout passes, etc.)
    animate()
        ?.alpha(0f)
        ?.setDuration(shortAnimationDuration.toLong())
        ?.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.INVISIBLE
            }
        })
}

fun NestedScrollView.setAppBarElevationListener(appBar: AppBarLayout?) {
    setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, _, _, _ ->
        appBar?.let {
            if (canScrollVertically(-1)) ViewCompat.setElevation(it, 6f)
            else ViewCompat.setElevation(it, 0f)
        }
    })
}

fun RecyclerView.setAppBarElevationListener(appBar: AppBarLayout?) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            appBar?.let {
                if (canScrollVertically(-1)) ViewCompat.setElevation(it, 6f)
                else ViewCompat.setElevation(it, 0f)
            }
        }
    })
}