package com.example.baseapplication.util

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A simple class using Delegate pattern of Kotlin to simplify set/get a SharePreference
 */
@Suppress("UNCHECKED_CAST")
class SharePreferenceDelegate<T : Any?>(
    private val sp: SharedPreferences,
    private val default: T,
    private val key: String,
    private val className: Class<*>
) : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        // don't detect type of class from default type, it leads error when default is set to null
        return when (className) {
            Int::class.java -> {
                sp.getInt(key, default as Int) as T
            }
            Long::class.java -> {
                sp.getLong(key, default as Long) as T
            }
            Float::class.java -> {
                sp.getFloat(key, default as Float) as T
            }
            Boolean::class.java -> {
                sp.getBoolean(key, default as Boolean) as T
            }
            String::class.java -> {
                sp.getString(key, default as String?) as T
            }
            else -> {
                val type = TypeToken.get(className).type
                val str = sp.getString(key, default as String?) ?: return null as T
                return Gson().fromJson(str, type) as T
            }
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        // set null if want to remove sharepreference variable
        if (value == null) {
            remove()
            return
        }
        val editor = sp.edit()
        when (value) {
            is Int -> {
                editor.putInt(key, value)
            }
            is Long -> {
                editor.putLong(key, value)
            }
            is Float -> {
                editor.putFloat(key, value)
            }
            is Boolean -> {
                editor.putBoolean(key, value)
            }
            is String -> {
                editor.putString(key, value)
            }
            else -> {
                val str = Gson().toJson(value)
                editor.putString(key, str)
            }
        }
        editor.apply()
    }

    private fun remove() {
        sp.edit().remove(key).apply()
    }
}

