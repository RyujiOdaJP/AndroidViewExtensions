package jp.co.arsaga.extensions.view

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

@BindingAdapter("binding_currentIndex", "binding_isAnimate", requireAll = false)
fun ViewPager.currentIndex(index: Int, isAnimate: Boolean?) {
    if (index < 0) return
    if (index > this.adapter?.count ?: 0) return
    this.setCurrentItem(index, (isAnimate ?: true))
}

@BindingAdapter("binding_currentIndex", "binding_isAnimate", requireAll = false)
fun ViewPager2.currentIndex(index: Int, isAnimate: Boolean?) {
    if (index < 0) return
    if (index > this.adapter?.itemCount ?: 0) return
    this.setCurrentItem(index, (isAnimate ?: true))
}

@Suppress("UNUSED_PARAMETER")
@BindingAdapter("binding_pageObserve")
fun ViewPager2.pageObserve(collection: Collection<Any>?) {
    adapter?.notifyDataSetChanged()
}
