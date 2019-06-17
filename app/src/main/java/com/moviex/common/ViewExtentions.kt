package com.moviex.common

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide


fun View.showOrHide(show: Boolean) {
    isVisible = show
}

fun View.showOrDissapear(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.INVISIBLE
}

fun ImageView.downloadImage(imageUrl: String) {
    Glide.with(context)
        .load(imageUrl)
        .into(this)
}

fun EditText.onTextChanged(text: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            text(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}