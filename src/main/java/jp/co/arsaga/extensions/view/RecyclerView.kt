package jp.co.arsaga.extensions.view

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.co.arsaga.extensions.viewModel.DiffRefreshEvent

@Suppress("UNUSED_PARAMETER")
@BindingAdapter("binding_listObserve")
fun RecyclerView.listObserve(collection: Collection<Any>?) {
    val currentViewHolderSize = layoutManager?.childCount
    adapter?.notifyDataSetChanged()
    if (currentViewHolderSize == 0) scheduleLayoutAnimation()
}

@BindingAdapter("binding_diffObserve")
fun RecyclerView.diffObserve(diffResult: DiffUtil.DiffResult?) {
    val currentViewHolderSize = layoutManager?.childCount
    adapter?.run { diffResult?.dispatchUpdatesTo(this) }
    if (currentViewHolderSize == 0) scheduleLayoutAnimation()
}
@BindingAdapter("binding_diffScrollObserve")
fun RecyclerView.diffScrollObserve(diffRefreshEvent: DiffRefreshEvent?) {
    val currentViewHolderSize = layoutManager?.childCount
    diffRefreshEvent?.also {
        adapter?.run { it.diffResult.dispatchUpdatesTo(this) }
        if (it.scrollPosition >= 0) observeAnimationFinish(this, Handler(Looper.getMainLooper())) {
            scrollToPosition(it.scrollPosition)
        }
    }
    if (currentViewHolderSize == 0) scheduleLayoutAnimation()
}
private fun observeAnimationFinish(recyclerView: RecyclerView, handler: Handler, callback: () -> Unit) {
    if (!recyclerView.isAnimating) callback()
    else handler.postDelayed({
        observeAnimationFinish(recyclerView, handler, callback)
    }, 100L)
}

@BindingAdapter("binding_divider")
fun RecyclerView.divider(drawable: Drawable?) {
    drawable ?: return
    layoutManager?.also {
        if (it is LinearLayoutManager) {
            DividerItemDecoration(context, it.orientation).apply {
                setDrawable(drawable)
                addItemDecoration(this)
            }
        }
    }
}

class BindingViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)

fun RecyclerView.sharedViewPool(recycledViewPool: RecyclerView.RecycledViewPool, @LayoutRes itemLayoutId: Int) {
    recycledViewPool.setMaxRecycledViews(itemLayoutId, Int.MAX_VALUE)
    setRecycledViewPool(recycledViewPool)
}

abstract class MergeDataBindingAdapter : DataBindingAdapter<ViewDataBinding>() {
    override fun onCreateViewDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewDataBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
}

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

fun RecyclerView.Adapter<out RecyclerView.ViewHolder>.isLastItem(position: Int): Boolean = itemCount -1 == position