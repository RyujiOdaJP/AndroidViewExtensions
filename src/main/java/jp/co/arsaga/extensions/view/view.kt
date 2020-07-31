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
import kotlin.math.hypot


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

@BindingAdapter("binding_visibility", "binding_height")
fun View.expandVerticallyAnimation(visibility: Boolean, height: Float) {
    if(visibility) {
        ValueAnimator.ofInt(0, convertDpToPx(context, height)).setDuration(300).let { animator ->
            animator.addUpdateListener {
                layoutParams.height = it.animatedValue as Int
                requestLayout()
            }
            animator.doOnStart {
                this.visibility = View.VISIBLE
            }
            animator.start()
        }
    } else {
        ValueAnimator.ofInt(convertDpToPx(context, height), 0).setDuration(300).let { animator ->
            animator.addUpdateListener {
                layoutParams.height = it.animatedValue as Int
                requestLayout()
            }
            animator.doOnEnd {
                this.visibility = View.GONE
            }
            animator.start()
        }
    }
}

private fun convertDpToPx(context: Context, dp: Float): Int {
    val d = context.resources.displayMetrics.density
    return ((dp * d) + 0.5).toInt()
}
