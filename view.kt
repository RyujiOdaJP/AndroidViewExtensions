package jp.co.arsaga.extensions.view

import android.animation.Animator
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import kotlin.math.hypot


val viewSizeMaximizeParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

fun View.getAppearanceAnimator(centerX: Int, centerY: Int): Animator = hypot(width / 2.toDouble(), height / 2.toDouble()).toFloat()
    .let { ViewAnimationUtils.createCircularReveal(this, centerX, centerY, 0f, it).apply { duration = 500L } }

fun View.getAppearanceAnimator(motionEvent: MotionEvent): Animator = getAppearanceAnimator(motionEvent.x.toInt(), motionEvent.y.toInt())