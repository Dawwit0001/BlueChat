package winged.example.core.utils.extensions

import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.extractText(): String {
    return this.text.toString().trim()
}
