package jp.co.arsaga.extensions.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNUSED_PARAMETER")
@BindingAdapter("binding_listObserve")
fun RecyclerView.listObserve(collection: Collection<Any>?) {
    val currentViewHolderSize = layoutManager?.childCount
    adapter?.notifyDataSetChanged()
    if (currentViewHolderSize == 0) scheduleLayoutAnimation()
}

@BindingAdapter("binding_listDiff")
fun RecyclerView.listDiff(diffResult: DiffUtil.DiffResult) {
    adapter?.run { diffResult.dispatchUpdatesTo(this) }
}