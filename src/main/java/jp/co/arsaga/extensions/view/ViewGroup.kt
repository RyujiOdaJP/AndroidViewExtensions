package jp.co.arsaga.extensions.view

import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior

@BindingAdapter("binding_BottomSheetPeekHeight", "binding_BottomSheetIsHideable", requireAll = false)
fun ViewGroup.bottomPeekHeight(
    px: Float,
    BottomSheetIsHideable: Boolean? = null
): BottomSheetBehavior<ViewGroup> = BottomSheetBehavior<ViewGroup>().apply {
    peekHeight = px.toInt()
    isHideable = BottomSheetIsHideable ?: false
}.also {
    layoutParams = (layoutParams as CoordinatorLayout.LayoutParams).apply {
        behavior = it
    }
}

@BindingAdapter("binding_addLayout")
fun ViewGroup.addLayout(layoutId: Int?) {
    layoutId ?: return
    if (layoutId == 0) return
    View.inflate(this.context, layoutId, null).run { addView(this) }
}

@BindingAdapter("binding_replaceLayout")
fun ViewGroup.replaceLayout(layoutId: Int?) {
    layoutId ?: return
    if (layoutId == 0) return
    this.removeAllViews()
    addLayout(layoutId)
}
