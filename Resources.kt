package jp.co.arsaga.extensions.view

import android.content.res.Resources

fun Resources.systemUiSize(systemUiName: SystemUiName): Int = getIdentifier(systemUiName.resourceName, "dimen", "android")
    .takeIf { it > 0 }
    ?.let { getDimensionPixelSize(it) }
    ?: 0

enum class SystemUiName(val resourceName: String) {
    NAVIGATION_BAR_HEIGHT("navigation_bar_height"),
    STATUS_BAR_HEIGHT("status_bar_height")
}