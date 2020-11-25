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
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.*
import jp.co.arsaga.extensions.viewModel.DiffRefreshEvent

@Suppress("UNUSED_PARAMETER")
@BindingAdapter("binding_listObserve")
fun RecyclerView.listObserve(collection: Collection<Any>?) {
    val currentViewHolderSize = layoutManager?.childCount
    adapter?.notifyDataSetChanged()
    if (currentViewHolderSize == 0) scheduleLayoutAnimation()
}

@BindingAdapter("binding_listDiffObserve")
fun <T> RecyclerView.listDiffObserve(collection: List<T>?) {
    val currentViewHolderSize = layoutManager?.childCount
    adapter?.let {
        @Suppress("UNCHECKED_CAST")
        (it as ListAdapter<T, *>).submitList(collection)
    }
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

private fun observeAnimationFinish(
    recyclerView: RecyclerView,
    handler: Handler,
    callback: () -> Unit
) {
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

fun RecyclerView.sharedViewPool(
    recycledViewPool: RecyclerView.RecycledViewPool,
    @LayoutRes itemLayoutId: Int
) {
    recycledViewPool.setMaxRecycledViews(itemLayoutId, Int.MAX_VALUE)
    setRecycledViewPool(recycledViewPool)
}

abstract class MergeDataBindingAdapter(lifecycleOwner: LifecycleOwner) :
    DataBindingAdapter<ViewDataBinding>(lifecycleOwner) {
    override fun onCreateViewDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewDataBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
}

abstract class MergeListBindingAdapter<Item>(
    lifecycleOwner: LifecycleOwner,
    callback: DiffUtil.ItemCallback<Item>
) : ListBindingAdapter<ViewDataBinding, Item>(lifecycleOwner, callback) {
    override fun onCreateViewDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewDataBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
}

abstract class ListBindingAdapter<
        Binding : ViewDataBinding,
        Item
        >(
    val lifecycleOwner: LifecycleOwner,
    callback: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, BindingViewHolder<Binding>>(callback) {
    abstract fun onCreateViewDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): Binding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<Binding> = LayoutInflater.from(parent.context)
        .run { onCreateViewDataBinding(this, parent, viewType) }
        .run { BindingViewHolder(this) }

    abstract fun onBindViewDataBinding(binding: Binding, position: Int)

    override fun onBindViewHolder(holder: BindingViewHolder<Binding>, position: Int) {
        holder.binding.also {
            it.lifecycleOwner = lifecycleOwner
            onBindViewDataBinding(it, position)
            it.executePendingBindings()
        }
    }
}

abstract class DataBindingAdapter<T : ViewDataBinding>(val lifecycleOwner: LifecycleOwner?) :
    RecyclerView.Adapter<BindingViewHolder<T>>() {
    abstract fun onCreateViewDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): T

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<T> = LayoutInflater.from(parent.context)
        .run { onCreateViewDataBinding(this, parent, viewType) }
        .run { BindingViewHolder(this) }

    abstract fun onBindViewDataBinding(binding: T, position: Int)

    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        holder.binding.also {
            it.lifecycleOwner = lifecycleOwner
            onBindViewDataBinding(it, position)
            it.executePendingBindings()
        }
    }
}

fun RecyclerView.Adapter<out RecyclerView.ViewHolder>.isLastItem(position: Int): Boolean =
    itemCount - 1 == position