package jp.co.arsaga.extensions.view

import android.content.Context
import android.content.SharedPreferences
import android.util.AttributeSet
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import timber.log.Timber

class RadioGroupSharedPreferences : RadioGroup {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs)

    internal var preferenceKey: String? = null
        set(value) {
            field = value
            initialize()
        }

    internal var preference: SharedPreferences? = null
        set(value) {
            field = value
            initialize()
        }

    private fun initialize() {
        preference?.apply { preferenceKey?.let { getString(it, null) }?.let { radioButtonTitle ->
            Timber.d("RadioGroupEncryptedSharedPreferencesValueTest:$radioButtonTitle")
            this@RadioGroupSharedPreferences.apply {
                children
                    .filterIsInstance<RadioButton>()
                    .find { it.text.toString() == radioButtonTitle }
                    ?.run { isChecked = true }
            }
        } }
    }

    init {
        setOnCheckedChangeListener { _, checkedId ->
            val radioButtonText = findViewById<RadioButton>(checkedId)?.text.toString()
            preference?.edit()
                ?.run { preferenceKey?.let { putString(it, radioButtonText) }?.apply() }
            Timber.d("RadioGroupEncryptedSharedPreferencesKeyTest:$preferenceKey: $radioButtonText")
        }
    }
}

@BindingAdapter("binding_preferenceKey")
fun RadioGroupSharedPreferences.preferenceKey(key: String?) {
    this.preferenceKey = key
}
@BindingAdapter("binding_preference")
fun RadioGroupSharedPreferences.preference(sharedPreferences: SharedPreferences?) {
    this.preference = sharedPreferences
}