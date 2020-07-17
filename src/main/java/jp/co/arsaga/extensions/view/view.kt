package jp.co.arsaga.extensions.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
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
fun View.slideAnimation(visibility: Boolean, height: Float) {
    val slideAnimator = if(visibility) {
        ValueAnimator.ofInt(0, height.toInt()).setDuration(300)
    } else {
        ValueAnimator.ofInt(height.toInt(), 0).setDuration(300)
    }

    slideAnimator.addUpdateListener {
        layoutParams.height = it.animatedValue as Int
        requestLayout()
    }

    slideAnimator.start()
}
