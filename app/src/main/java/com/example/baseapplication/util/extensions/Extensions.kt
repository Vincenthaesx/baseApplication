package com.example.baseapplication.util.extensions

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Environment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

inline fun <reified T : Any> Any?.safeCast(action: T.() -> Unit) {
    if (this is T) action.invoke(this)
}

fun getTodayDate(): String {
    val c = Calendar.getInstance()
    val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return df.format(c.time)
}

fun FragmentManager.currentFragment(): Fragment? = this.fragments.lastOrNull { it.isVisible }

fun FragmentTransaction.safeHide(frag: Fragment?): FragmentTransaction {
    return if (frag != null) hide(frag) else this
}

fun saveBitmapToInternalStorage(bitmapImage: Bitmap): String {
    val rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val time = System.currentTimeMillis()
    val myPath = File(rootPath, "$time.jpg")
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(myPath)
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        fos?.close()
    }
    return myPath.absolutePath
}

/**
 * Used to avoid if else statement.
 */
fun Boolean.onTrue(action: () -> Unit): Boolean {
    if (this) action.invoke()
    return this
}

/**
 * Used to avoid if else statement.
 */
fun Boolean.onFalse(action: () -> Unit): Boolean {
    if (!this) action.invoke()
    return this
}

fun <T> MutableList<T>.replace(oldItem: T, newItem: T): MutableList<T> {
    val pos = indexOf(oldItem).takeIf { it != -1 } ?: return this
    set(pos, newItem)
    return this
}

// connectivity

fun Context.isConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    //should check null because in airplane mode it will be null
    return netInfo != null && netInfo.isConnected
}