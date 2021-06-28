package jp.co.arsaga.extensions.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.Headers
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.BitmapImageViewTarget

@BindingAdapter("binding_url", "binding_shapingType", "binding_shapingParam", "binding_headers", requireAll = false)
fun ImageView.imageUrl(imageUrl: String?, shapingType: ShapingType?, shapingParam: Float?, headers: Pair<String, String>?) =
    imageUrl?.let { Glide.with(context).asBitmap()
        .load( headers?.let { headers -> GlideUrl(it, setHeaders(headers)) } ?: it)
        .attachImage(this, shapingType, shapingParam) }

@BindingAdapter("binding_resource", "binding_shapingType", "binding_shapingParam", requireAll = false)
fun ImageView.resourceId(resourceId: Int?, shapingType: ShapingType?, shapingParam: Float?) = resourceId?.let { Glide.with(context).asBitmap().load(it).attachImage(this, shapingType, shapingParam) }

@BindingAdapter("binding_drawable", "binding_shapingType", "binding_shapingParam", requireAll = false)
fun ImageView.imageDrawable(drawable: Drawable?, shapingType: ShapingType?, shapingParam: Float?) = drawable?.let { Glide.with(context).asBitmap().load(it).attachImage(this, shapingType, shapingParam) }

@BindingAdapter("binding_bitmap", "binding_shapingType", "binding_shapingParam", requireAll = false)
fun ImageView.imageDrawable(bitmap: Bitmap?, shapingType: ShapingType?, shapingParam: Float?) = bitmap?.let { Glide.with(context).asBitmap().load(it).attachImage(this, shapingType, shapingParam) }

private fun RequestBuilder<Bitmap>.attachImage(
    imageView: ImageView,
    shapingType: ShapingType?,
    shapingParam: Float?
) = if (shapingType == null) into(imageView)
else shapingType.shaper(imageView, shapingParam)?.run { into(this) }

private fun setHeaders(pair: Pair<String, String>): Headers =
    LazyHeaders.Builder()
        .addHeader(pair.first, pair.second)
        .build()

enum class ShapingType(val shaper: (ImageView, Float?) -> BitmapImageViewTarget?) {
    CIRCLE({ imageView: ImageView, _: Float? ->
        object:BitmapImageViewTarget(imageView){
            override fun setResource(resource:Bitmap?){
                RoundedBitmapDrawableFactory
                    .create(imageView.context.resources, resource).apply {
                        isCircular = true
                    }.run {
                        imageView.setImageDrawable(this)
                    }
            }
        }
    }),
    ROUND({ imageView: ImageView, float: Float? ->
        object:BitmapImageViewTarget(imageView){
            override fun setResource(resource:Bitmap?){
                float ?: return
                RoundedBitmapDrawableFactory
                    .create(imageView.context.resources, resource).apply {
                        cornerRadius = float
                        setAntiAlias(true)
                    }.run {
                        imageView.setImageDrawable(this)
                    }
            }
        }
    })
}
