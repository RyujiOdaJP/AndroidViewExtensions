package jp.co.arsaga.extensions.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import kotlin.math.min

@BindingAdapter("binding_setText")
fun EditText.addYen(input: String) {
    if(text.toString().replace("¥","") != input || text.first() != '¥') {
        val cursorPosition = selectionStart
        val commaCount = text.count { it ==',' }
        ("¥$input").apply { setText(this) }
        filters
            .run { find { it is InputFilter.LengthFilter } as? InputFilter.LengthFilter }
            ?.takeIf { cursorPosition < it.max }
            ?.let { min(cursorPosition + (text.count { it ==',' } - commaCount), text.length) }
            ?.let { setSelection(it) }
            ?: setSelection(input.length + YEN_LENGTH)
    }
}

private const val YEN_LENGTH = 1

@InverseBindingAdapter(attribute = "binding_setText", event = "android:textAttrChanged")
fun EditText.addComma(): String = text.toString()
    .takeIf { it.length > 1}
    ?.replace(",", "")
    ?.replace("¥", "")
    ?.let { String.format("%,d", it.toInt()) }
    ?: "0"

@BindingAdapter("binding_alreadyFocused", "binding_focusedId", requireAll = false)
fun EditText.alreadyFocused(focusedViewIdSet: MutableSet<Int>?, focusedId: Int?) {
    setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) focusedViewIdSet?.add(focusedId ?: id)
    }
}

@BindingAdapter("binding_focusChanged")
fun EditText.focusChanged(focusChanged: View.OnFocusChangeListener?) {
    onFocusChangeListener = focusChanged
}

@BindingAdapter("binding_editTextError", "binding_errorMessage", "binding_errorIcon", requireAll = false)
fun EditText.errorMessage(isError: Boolean?,errorMessage: String?, errorIcon: Drawable?) {
    if (isError == true) setError(errorMessage, errorIcon)
    else setError(null, null)
}

@BindingAdapter("binding_onEnterKey")
fun EditText.onEnterKey(onEnterKey: View.OnClickListener?) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId in listOf(EditorInfo.IME_ACTION_NEXT, actionId == EditorInfo.IME_ACTION_DONE)) {
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(windowToken, 0)
            onEnterKey?.onClick(this)
        }
        false
    }
}
