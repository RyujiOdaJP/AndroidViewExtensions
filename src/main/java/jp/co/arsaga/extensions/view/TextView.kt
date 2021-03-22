package jp.co.arsaga.extensions.view

import android.graphics.Typeface
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("binding_textStyle")
fun TextView.textStyle(textStyle: Int?) {
    setTypeface(null, textStyle ?: Typeface.NORMAL)
}
