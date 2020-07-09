package jp.co.arsaga.extensions.view

import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior

@BindingAdapter("binding_BottomSheetPeekHeight")
fun ViewGroup.bottomPeekHeight(px: Float) {
    layoutParams = (layoutParams as CoordinatorLayout.LayoutParams).apply {
        behavior = BottomSheetBehavior<ViewGroup>().apply {
            peekHeight = px.toInt()
            isHideable = false
        }
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
