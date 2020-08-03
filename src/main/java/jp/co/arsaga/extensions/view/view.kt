package jp.co.arsaga.extensions.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.databinding.BindingAdapter
import kotlin.math.floor
import kotlin.math.hypot
import kotlin.math.round


val viewSizeMaximizeParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

fun View.getAppearanceAnimator(centerX: Int, centerY: Int): Animator = hypot(width / 2.toDouble(), height / 2.toDouble()).toFloat()
    .let { ViewAnimationUtils.createCircularReveal(this, centerX, centerY, 0f, it).apply { duration = 500L } }

fun View.getAppearanceAnimator(motionEvent: MotionEvent): Animator = getAppearanceAnimator(motionEvent.x.toInt(), motionEvent.y.toInt())

@BindingAdapter("binding_toggle")
fun View.toggle(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("binding_hide")
fun View.hide(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

@BindingAdapter("binding_visibility")
fun View.expandVerticallyAnimation(visibility: Boolean) {
    if (visibility) {
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED).let { measure(it, it) }
        ValueAnimator.ofInt(0, measuredHeight).also {
            it.doOnStart { this.visibility = View.VISIBLE }
        }
    } else {
        ValueAnimator.ofInt(height, 0).also {
            it.doOnEnd { this.visibility = View.GONE }
        }
    }.let { animator ->
        animator.duration = resources.getInteger(R.integer.animate_duration).toLong()
        animator.addUpdateListener {
            layoutParams = layoutParams.also { params ->
                params.height = it.animatedValue as Int
            }
        }
        animator.start()
    }
}

private fun convertDpToPx(context: Context, dp: Float): Int {
    val d = context.resources.displayMetrics.density
    return round(dp * d).toInt()
}
