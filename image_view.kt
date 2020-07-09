package jp.co.arsaga.extensions.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:src")
fun ImageView.imageUrl(imageUrl: String?) = imageUrl?.let { Glide.with(context).load(it).into(this) }

@BindingAdapter("android:src")
fun ImageView.resourceId(resourceId: Int?) = resourceId?.let { Glide.with(context).load(it).into(this) }

@BindingAdapter("android:src")
fun ImageView.imageDrawable(drawable: Drawable?) = drawable?.let { Glide.with(context).load(it).into(this) }

@BindingAdapter("binding_bitmap")
fun ImageView.imageDrawable(bitmap: Bitmap?) = bitmap?.let { Glide.with(context).load(it).into(this) }

