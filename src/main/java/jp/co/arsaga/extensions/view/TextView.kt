package jp.co.arsaga.extensions.view

import android.app.Activity
import android.app.Application
import android.content.Context.WINDOW_SERVICE
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.BindingAdapter


@BindingAdapter("binding_textStyle")
fun TextView.textStyle(textStyle: Int?) {
    setTypeface(null, textStyle ?: Typeface.NORMAL)
}

class IgnoreUserFontScale : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ignoreUserFontScale(activity)
    }
    override fun onActivityResumed(activity: Activity) {
        ignoreUserFontScale(activity)
    }
    override fun onActivityPaused(activity: Activity) {
        ignoreUserFontScale(activity)
    }

    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}

    private fun ignoreUserFontScale(activity: Activity) {
        activity.run {
            val configuration = resources.configuration.also { it.fontScale = 1.0f }
            val metrics = resources.displayMetrics
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            wm.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            baseContext.resources.updateConfiguration(configuration, metrics)
        }
    }
}
