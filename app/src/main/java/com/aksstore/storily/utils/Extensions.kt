package com.aksstore.storily.utils

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions

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

