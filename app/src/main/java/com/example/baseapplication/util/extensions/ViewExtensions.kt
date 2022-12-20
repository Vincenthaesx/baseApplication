package com.example.baseapplication.util.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.baseapplication.global.BaseApplication

/*------------------------------------Visibility----------------------------------------*/
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    clearAnimation()
    visibility = View.INVISIBLE
}

fun View.gone() {
    clearAnimation()
    visibility = View.GONE
}

fun View.visible(isVisible: Boolean) {
    isVisible.onTrue { visible() }.onFalse { gone() }
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.enable(enabled: Boolean) {
    enabled.onTrue { enable() }.onFalse { disable() }
}

fun RecyclerView.horizontalLayout() {
    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
}

fun RecyclerView.verticalLayout() {
    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
}

fun ViewGroup.inflate(@LayoutRes id: Int, attachedToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(id, this, attachedToRoot)
}

fun ImageView.setImageWithGlide(url: String?) {
    Glide.with(BaseApplication.globalContext)
        .load(url)
        .centerCrop()
        .into(this)
}

fun EditText.onAfterTextChanged(action: (Editable) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            action.invoke(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}