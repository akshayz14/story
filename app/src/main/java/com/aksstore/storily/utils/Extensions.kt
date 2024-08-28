package com.aksstore.storily.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import com.aksstore.storily.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.ViewPropertyTransition

fun NavController.navigateSafe(@IdRes resId: Int, args: Bundle? = null) {
    val destinationId = currentDestination?.getAction(resId)?.destinationId
    try {
        if (currentDestination != null) {
            currentDestination?.let { node ->
                val currentNode = when (node) {
                    is NavGraph -> node
                    else -> node.parent
                }
                if (destinationId != null) {
                    currentNode?.findNode(destinationId)?.let { navigate(resId, args) }
                }
            }
        } else {
            Log.d("dukaanError::::", "destination is null")
        }
    } catch (e: Exception) {
        Log.d("dukaanError::::", e.printStackTrace().toString())
    }
}

fun NavController.navigateSafeWithOption(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
) {
    val destinationId = currentDestination?.getAction(resId)?.destinationId
    currentDestination?.let { node ->
        val currentNode = when (node) {
            is NavGraph -> node
            else -> node.parent
        }
        if (destinationId != null) {
            currentNode?.findNode(destinationId)?.let { navigate(resId, args, navOptions) }
        }
    }
}

fun AssetManager.readAssetsFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }


fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes errorDrawable: Int? = null
) {
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .also { glide ->
            val requestOptions = RequestOptions()
            placeholder?.also { drawable ->
                requestOptions.placeholder(drawable)
            }
            errorDrawable?.let { errorDrawable ->
                requestOptions.error(errorDrawable)
            }
            glide.apply(requestOptions)

        }.into(this)
}

fun ImageView.loadImageWithThumb(
    url: String?,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes errorDrawable: Int? = null
) {
    Glide.with(this)
        .load(url)
        .thumbnail(Glide.with(this).load(R.drawable.ic_loading))
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .also { glide ->
            val requestOptions = RequestOptions()
            placeholder?.also { drawable ->
                requestOptions.placeholder(drawable)
            }
            errorDrawable?.let { errorDrawable ->
                requestOptions.error(errorDrawable)
            }
            glide.apply(requestOptions)

        }.into(this)
}
 fun Int.dpToPx(resources: Resources): Int {
    val scale = resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun isNightMode(context: Context): Boolean {
    val nightModeFlags =
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
}

 fun getSelectedText(tvStory: TextView): String {
    val start = tvStory.selectionStart
    val end = tvStory.selectionEnd
    return if (start in 0..<end) {
        tvStory.text.substring(start, end)
    } else {
        ""
    }
}

var animationObject =
    ViewPropertyTransition.Animator { view ->
        view.alpha = 0f
        val fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        fadeAnim.setDuration(2500)
        fadeAnim.start()
    }