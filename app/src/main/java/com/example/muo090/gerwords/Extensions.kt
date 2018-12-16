package com.example.muo090.gerwords

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText

fun BasePresenter.empty(string: String?) = string?.isBlank() ?: true
fun BasePresenter.notEmpty(string: String?) = !empty(string)

fun EditText.empty() = text.toString().trim().isBlank()
fun EditText.notEmpty() = !empty()
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