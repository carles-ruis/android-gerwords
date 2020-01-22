package com.example.muo090.gerwords

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

fun DialogFragment.inflate(@LayoutRes layoutRes : Int) = LayoutInflater.from(this.context).inflate(layoutRes, null)

fun EditText.empty() = text.toString().trim().isBlank()
fun EditText.content() = text.toString().trim()

fun EditText.setAfterTextChangedListener(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // nothing to do here
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // nothing to do here
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}