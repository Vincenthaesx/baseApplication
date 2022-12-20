package com.example.baseapplication.util.extensions

import android.R
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


/*------------------------------------Toast----------------------------------------*/

fun Context.toast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        val t = toast.view!!.findViewById<View>(R.id.message) as TextView
        t.textSize = 32F
        t.gravity = Gravity.CENTER
    }
    toast.show()
}

fun Fragment.toast(message: String) {
    contextFragment.toast(message)
}