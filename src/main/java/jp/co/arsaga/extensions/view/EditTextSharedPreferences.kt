package jp.co.arsaga.extensions.view

import android.content.Context
import android.content.SharedPreferences
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import timber.log.Timber

class EditTextSharedPreferences : androidx.appcompat.widget.AppCompatEditText {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        ctx,
        attrs,
        defStyleAttr
    )

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

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        preference
            ?.edit()
            ?.run { preferenceKey?.let { putString(it, text.toString()) }?.apply() }
        Timber.d("EditTextEncryptedSharedPreferencesKeyTest:$preferenceKey")
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    private fun initialize() {
        preference?.run { preferenceKey?.let { getString(it, "") }?.let {
            Timber.d("EditTextEncryptedSharedPreferencesValueTest:$it")
            this@EditTextSharedPreferences.setText(it, BufferType.NORMAL)
        } }
    }
}

@BindingAdapter("binding_preferenceKey")
fun EditTextSharedPreferences.preferenceKey(key: String?) {
    this.preferenceKey = key
}
@BindingAdapter("binding_preference")
fun EditTextSharedPreferences.preference(sharedPreferences: SharedPreferences?) {
    this.preference = sharedPreferences
}