package jp.co.arsaga.extensions.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
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

class BindingViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)

abstract class DataBindingAdapter<T : ViewDataBinding> : RecyclerView.Adapter<BindingViewHolder<T>>() {
    abstract fun onCreateViewDataBinding(layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int): T

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<T> = LayoutInflater.from(parent.context)
        .run { onCreateViewDataBinding(this, parent, viewType) }
        .run { BindingViewHolder(this) }

    abstract fun onBindViewDataBinding(binding: T, position: Int)

    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        onBindViewDataBinding(holder.binding, position)
    }
}